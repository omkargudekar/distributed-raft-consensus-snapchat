package com.distsc.server.listener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;

public class ListenerThread implements Runnable
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
			b.group(group).channel(NioSocketChannel.class).handler(new ListenerInitializer());
			RequestContext requestContext;
			Request request;
			while (true)
			{
				if (ListnerConnectionRequestQueue.getCount() > 0)
				{	requestContext=ListnerConnectionRequestQueue.pop();
					request=requestContext.getRequest();
					try
					{

						ch = b.connect(request.getPayload().getNodeDiscovery().getNODEIP(),request.getPayload().getNodeDiscovery().getNODEPORT()).sync().channel();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Request getAcceptConnectionMsg()
	{
		
		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_ACCEPTED).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

	public Request getRejectConnectionMessage()
	{

		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_REJECTED).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

}
