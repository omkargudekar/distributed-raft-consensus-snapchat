package com.distc.cluster.discovery;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distc.cluster.config.ClusterConfiguration;
import com.distc.cluster.map.InboundClusterContextMap;
import com.distc.cluster.proto.App;
import com.distc.cluster.proto.App.Request;
import com.distc.cluster.server.ClusterNode;
import com.distsc.app.config.GlobalConfiguration;


public class ClusterDiscoveryThread implements Runnable
{
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;
    private ClusterDiscoveryHandler clusterDisc=null;
	public void run()
	{
		System.out.println("ClusterDiscoveryThread Started...");
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ClusterDiscoveryInitializer());
			while (true)
			{
				try
				{
					for (ClusterNode node : ClusterConfiguration.getNodes())
					{
						if (!InboundClusterContextMap.isChannelExist(node.getClusterID()))
						{
							System.out.println("Trying to connect...."+node.getNodeIP());
							ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
							System.out.println("Connected....");
							lastWriteFuture = ch.writeAndFlush(sendJoinMessage());
							System.out.println("Sending Message to "+node.getNodeIP()+node.getNodePort());
							InboundClusterContextMap.addClusterContextChnnelContext(node.getClusterID(),ch);
						}
						
					}
					pause();

				}
				catch (Exception e)
				{
					System.out.println(e);
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
				lastWriteFuture.channel().close().sync();
				group.shutdownGracefully();
			}
			catch (Exception e)
			{
					System.out.println(e);
			}
		}

	}

	public boolean isChannelActive(ChannelHandlerContext ctx)
	{
		
		boolean writable=false;
		try
		{
			writable=true;
		}
		catch(Exception e)
		{
			writable=false;

		}
		
		return writable;
		
	}
	public void pause()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Request sendJoinMessage()
	{
		
		Request req=null;
		try
		{
		req= App.Request.newBuilder()
						  .setJoinMessage(
								  App.JoinMessage
								  .newBuilder()
								  .setFromClusterId(Integer.parseInt(ClusterConfiguration.getCurrentClusterNode().getClusterID()))
								  .setFromNodeId(Integer.parseInt(GlobalConfiguration.getCurrentNode().getNodeID())))
						  .build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return req;
	}


}
