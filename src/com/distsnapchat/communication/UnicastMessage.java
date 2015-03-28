package com.distsnapchat.communication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.ArrayList;



public class UnicastMessage implements Runnable
{
	private ArrayList<String> messages = new ArrayList<String>();

	static String HOST = null;
	static int PORT = 0;

	
	public UnicastMessage(String host, int port,String message)
	{
		HOST = host;
		PORT = port;
		pushMessage(message);
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

	public String popMessage()
	{
		if (getMessageCount() > 0)
		{
			String message = messages.get(0);
			messages.remove(0);
			return message;
		}

		return null;

	}

	public ArrayList<String> popMessages()
	{
		if (getMessageCount() > 0)
		{

			return messages;
		}

		return null;

	}

	public void pushMessage(String message)
	{
		messages.add(message);

	}

}
