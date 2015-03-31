package com.distsnapchat.communication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;

import org.dissnapchat.protobuf.MessageProto.Message;



public class UnicastMessage implements Runnable
{
	private ArrayList<Message> messages = new ArrayList<Message>();

	static String HOST = null;
	static int PORT = 0;

	
	public UnicastMessage(String host, int port,Message msg)
	{
		HOST = host;
		PORT = port;
		pushMessage(msg);
	}

	public void run()
	{

		EventLoopGroup group = null;
		ChannelFuture lastWriteFuture = null;
		try
		{
			group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new UnicastMessagetInitializer());
			Channel ch = b.connect(HOST, PORT).sync().channel();
			if (getMessageCount() > 0)
			{
					
					while (getMessageCount() > 0)
					{
						while (ch.isWritable() == false)
						{
							Thread.sleep(1000);
						}
						lastWriteFuture = ch.writeAndFlush(popMessage());
						ch.closeFuture().sync();
						if (lastWriteFuture != null)
						{
							lastWriteFuture.sync();
						}
						

					}
					
				}
				else
				{
					Thread.sleep(100);
				}
			
		}
		catch (Exception e)
		{

		}
		finally
		{
			group.shutdownGracefully();
		}

	}

	public int getMessageCount()
	{
		return messages.size();
	}

	public Message popMessage()
	{
		if (getMessageCount() > 0)
		{
			Message message = messages.get(0);
			messages.remove(0);
			return message;
		}

		return null;

	}

	public ArrayList<Message> popMessages()
	{
		if (getMessageCount() > 0)
		{

			return messages;
		}

		return null;

	}

	public void pushMessage(Message message)
	{
		messages.add(message);

	}

}
