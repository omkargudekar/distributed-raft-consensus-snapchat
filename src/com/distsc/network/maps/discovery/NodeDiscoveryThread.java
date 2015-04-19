package com.distsc.network.maps.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.beans.Node;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;

public class NodeDiscoveryThread implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(NodeDiscoveryThread.class);

	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;

	public void run()
	{
		logger.info("NodeDiscoveryThread Started...");
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new NodeDiscoveryInitializer());
			while (true)
			{
				try
				{
					for (Node node : GlobalConfiguration.getNodes())
					{
						if (!NodeChannelContextMap.isChannelExist(node.getNodeID()))
						{
							ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
							lastWriteFuture = ch.writeAndFlush(getNodeDiscoveryMessage());
							logger.info("Sending Message to "+node.getNodeIP()+node.getNodePort());
						}
						
					}
					pause();

				}
				catch (Exception e)
				{
					logger.error(e.toString());
				}

			}

		}
		catch (Exception e)
		{
			logger.error(e.toString());

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
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{

			logger.error(e.toString());
			e.printStackTrace();
		}
	}

	public Request getNodeDiscoveryMessage()
	{

		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.REQUEST_CONNECTION).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

}
