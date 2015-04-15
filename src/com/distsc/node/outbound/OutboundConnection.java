package com.distsc.node.outbound;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.beans.Node;
import com.distsc.beans.Packet;
import com.distsc.comm.msg.queues.outbound.MgmtMsgOutboundQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class OutboundConnection implements Runnable
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
			b.group(group).channel(NioSocketChannel.class).handler(new OutboundConnectionInitializer());
			
			System.out.println("Cluster Client Thread Started...");
			while(true)
			{
				if(MgmtMsgOutboundQueue.getMessageCount()>0)
				{
					try
					{
						packet=MgmtMsgOutboundQueue.popMessage();
						node=packet.getNode();
						msg=packet.getMsg();
					//	System.out.println("Message Received in Outbound Queue : "+msg.getMessageType());
						ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
						lastWriteFuture = ch.writeAndFlush(msg);
						lastWriteFuture.channel().close().sync();	
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
				else
				{
					pause();
				}
			}
	
		

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Client Terminated...");
			try
			{
				group.shutdownGracefully();
			}
			catch(Exception e)
			{
				
			}
		}

	}
	public void pause()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
