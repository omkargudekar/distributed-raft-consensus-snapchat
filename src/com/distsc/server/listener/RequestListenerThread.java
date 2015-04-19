package com.distsc.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.ListnerConnectionRequestQueue;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;

public class RequestListenerThread implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(RequestListenerThread.class);

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
				{	
					logger.info("Message Found in ListnerConnectionRequestQueue");
					requestContext=ListnerConnectionRequestQueue.pop();
					request=requestContext.getRequest();
					try
					{
						ch = b.connect(request.getPayload().getNodeDiscovery().getNODEIP(),request.getPayload().getNodeDiscovery().getNODEPORT()).sync().channel();
						lastWriteFuture = ch.writeAndFlush(getAcceptConnectionMsg());

					}
					catch (Exception e)
					{
						logger.info(e.toString());
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
			logger.info(e.toString());
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
				logger.error(e.toString());
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
		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_ACCEPTED).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

	public Request getRejectConnectionMessage()
	{

		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.RESPONSE_CONNECTION_REJECTED).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

}
