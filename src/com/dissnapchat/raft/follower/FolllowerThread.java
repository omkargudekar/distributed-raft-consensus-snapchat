package com.dissnapchat.raft.follower;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.ArrayList;

public class FolllowerThread implements Runnable
{
	private ArrayList<String> messages = new ArrayList<String>();

	static String HOST = null;
	static int PORT = 0;

	public FolllowerThread(String host, int port)
	{
		HOST = host;
		PORT = port;

	}
	
	public FolllowerThread(String host, int port,String message)
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
			b.group(group).channel(NioSocketChannel.class).handler(new FollowerInitializer());
			Channel ch = b.connect(HOST, PORT).sync().channel();

			while (true)
			{
				if (getMessageCount() > 0)
				{
					System.out.println("Sending Message...");
					while (getMessageCount() > 0)
					{

						while (ch.isWritable() == false)
						{
							System.out.println("Wating for channel to get active...");
							Thread.sleep(1000);
						}

						System.out.println("Channel Active...");
						lastWriteFuture = ch.writeAndFlush(popMessage());
						System.out.println("Message sent...");
						ch.closeFuture().sync();
						if (lastWriteFuture != null)
						{
							lastWriteFuture.sync();
						}
						else
						{
							System.out.println("LastWriteFuture Completed...");
						}

					}
				}
				else
				{
					System.out.println("Client : No Messages to send...");
					Thread.sleep(2000);
				}
			}
		}
		catch (Exception e)
		{

		}
		finally
		{
			group.shutdownGracefully();
			System.out.println("Unicast exit...");
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
