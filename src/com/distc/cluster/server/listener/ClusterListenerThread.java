package com.distc.cluster.server.listener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distc.cluster.config.ClusterConfiguration;
import com.distc.cluster.msg.queue.ClusterRequestContext;
import com.distc.cluster.proto.App;
import com.distc.cluster.proto.App.Request;

public class ClusterListenerThread implements Runnable
{
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;

	public void run()
	{
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ClusterListenerInitializer());
			ClusterRequestContext requestContext;
			Request request;
			while (true)
			{
				if (ClusterListnerConnectionRequestQueue.getCount() > 0)
				{	requestContext=ClusterListnerConnectionRequestQueue.pop();
					request=requestContext.getRequest();
					try
					{
						ch = b.connect(request.getBody().getNodeDiscovery().getNODEIP(),request.getBody().getNodeDiscovery().getNODEPORT()).sync().channel();
						lastWriteFuture = ch.writeAndFlush(getAcceptConnectionMsg());

					}
					catch (Exception e)
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
	
	public void pause()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public Request getAcceptConnectionMsg()
	{
		
		return App.Request.newBuilder().setBody(App.Payload.newBuilder().setNodeDiscovery(App.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(App.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_ACCEPTED).setNODEID(ClusterConfiguration.getClusterNode().getClusterID()).setNODEIP(ClusterConfiguration.getClusterNode().getNodeIP()).setNODEPORT(ClusterConfiguration.getClusterNode().getNodePort()))).build();
	}

	public Request getRejectConnectionMessage()
	{

		return App.Request.newBuilder().setBody(App.Payload.newBuilder().setNodeDiscovery(App.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(App.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_REJECTED).setNODEID(ClusterConfiguration.getClusterNode().getClusterID()).setNODEIP(ClusterConfiguration.getClusterNode().getNodeIP()).setNODEPORT(ClusterConfiguration.getClusterNode().getNodePort()))).build();
	}

}
