package com.distsc.intercluster.outbound;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;

import com.distsc.app.ClusterConfiguration;
import com.distsc.beans.Node;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;
import com.distsc.intercluster.msg.queues.outbound.OutboundInterClusterMsgQueue;

public class InterClusterOutboundConnection implements Runnable
{
	public void run()
	{

		
		EventLoopGroup group = null;
		ChannelFuture lastWriteFuture = null;
		ArrayList<Node> nodes=null;
		Channel ch=null;
		Message msg=null;
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new InterClusterOutboundConnectionInitializer());
			
			System.out.println("InterCluster Outbound Thread Started...");
			while(true)
			{
				if(OutboundInterClusterMsgQueue.getMessageCount()>0)
				{
					try
					{
						
						System.out.println("Preparing InterCluster Message");
						nodes=ClusterConfiguration.getNodes();
						ch = b.connect(nodes.get(0).getNodeIP(), nodes.get(0).getNodePort()).sync().channel();
						lastWriteFuture = ch.writeAndFlush(msg);
						lastWriteFuture.channel().close().sync();
						System.out.println("InterCluster Message Sent to "+nodes.get(0).getNodeIP());
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
