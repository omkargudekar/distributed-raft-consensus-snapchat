package com.distsc.comm.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.outbound.OutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class ClientConnection implements Runnable
{
	

	

	public void run()
	{

		
		EventLoopGroup group = null;
		ChannelFuture lastWriteFuture = null;
		Packet packet=null;
		Channel ch=null;
		Node node=null;
		Message msg=null;
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ClientConnectionInitializer());
			
			while(true)
			{
				if(OutboundQueue.getMessageCount()>0)
				{
					packet=OutboundQueue.popMessage();
					node=packet.getNode();
					msg=packet.getMsg();
					ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
					lastWriteFuture = ch.writeAndFlush(msg);
					lastWriteFuture.channel().close().sync();	
				}
			}
	
		

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{

			try
			{
				group.shutdownGracefully();
			}
			catch(Exception e)
			{
				
			}
		}

	}

}
