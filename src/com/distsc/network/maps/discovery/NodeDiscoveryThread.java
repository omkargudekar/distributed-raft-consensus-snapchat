package com.distsc.network.maps.discovery;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
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
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch = null;

	public void run()
	{
		System.out.println("NodeDiscoveryThread Started...");
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
							System.out.println("Sending Message to "+node.getNodeIP()+node.getNodePort());
						}
						
						else if(NodeChannelContextMap.isChannelExist(node.getNodeID()) && isChannelActive(NodeChannelContextMap.getNodeContext(node.getNodeID()))==false )
						{
							NodeChannelContextMap.removeNodeChannelContext(node.getNodeID());
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
		
		System.out.println("$$$$$ Check if channelActive");
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
		
		System.out.println("ChannelActive"+writable);

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

		return MessageProto.Request.newBuilder().setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
				.setPayload(MessageProto.Payload.newBuilder().setNodeDiscovery(MessageProto.NodeDiscovery.newBuilder().setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.REQUEST_CONNECTION).setNODEID(GlobalConfiguration.getCurrentNode().getNodeID()).setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP()).setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort()))).build();
	}

}
