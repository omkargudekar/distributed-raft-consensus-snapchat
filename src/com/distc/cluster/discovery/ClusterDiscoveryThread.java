package com.distc.cluster.discovery;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distc.cluster.config.ClusterConfiguration;
import com.distc.cluster.map.ClusterContextMap;
import com.distc.cluster.proto.App;
import com.distc.cluster.proto.App.Request;
import com.distc.cluster.server.listener.ClusterNode;


public class ClusterDiscoveryThread implements Runnable
{
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;

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
						if (!ClusterContextMap.isChannelExist(node.getClusterID()))
						{
							ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
							lastWriteFuture = ch.writeAndFlush(getNodeDiscoveryMessage());
							System.out.println("Sending Message to "+node.getNodeIP()+node.getNodePort());
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

			}
		}

	}

	public boolean isChannelActive(ChannelHandlerContext ctx)
	{
		
		boolean writable=false;
		try
		{
			ctx.write("Test");
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

	public Request getNodeDiscoveryMessage()
	{

		return App.Request.newBuilder().setBody(App.Payload.newBuilder().setNodeDiscovery(App.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(App.NodeDiscovery.NodeDiscoveryMessageType.REQUEST_CONNECTION).setNODEID(ClusterConfiguration.getClusterNode().getClusterID()).setNODEIP(ClusterConfiguration.getClusterNode().getNodeIP()).setNODEPORT(ClusterConfiguration.getClusterNode().getNodePort()))).build();
		
	}

}
