package com.distsnapchat.communication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


import org.dissnapchat.protobuf.MessageProto.Message;

public class UnicastMessage implements Runnable
{
	Message message = null;
	static String HOST = null;
	static int PORT = 0;

	public UnicastMessage(String host, int port, Message msg)
	{
		HOST = host;
		PORT = port;
		message = msg;
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

			lastWriteFuture = ch.writeAndFlush(message);
			lastWriteFuture.channel().closeFuture().sync();

		}
		catch (Exception e)
		{

		}
		finally
		{
			group.shutdownGracefully();
		}

	}

}
