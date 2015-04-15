package com.distsc.network.maps.discovery;
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
import com.distsc.network.maps.NetworkContextMap;
public class NodeDiscoveryThread implements Runnable
{
	private EventLoopGroup group = null;
	private ChannelFuture lastWriteFuture = null;
	private Channel ch=null;
	public void run()
	{
		try
		{
			group = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new NodeDiscoveryInitializer());
			
			try
			{		
				for(Node node : GlobalConfiguration.getNodes())
				{
					if(!NetworkContextMap.isChannelExist(node.getNodeID()))
					{
						ch = b.connect(node.getNodeIP(), node.getNodePort()).sync().channel();
						lastWriteFuture = ch.writeAndFlush(getNodeDiscoveryMessage());
						lastWriteFuture.channel().close().sync();	
					}
				}

			}
			catch(Exception e)
			{
						System.out.println(e);
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
	
	public Request getNodeDiscoveryMessage()
	{
		
			 return MessageProto.Request.newBuilder()
											.setMessageHeader(Request.MessageHeader.NodeDiscoveryMsg)
											.setPayload(MessageProto.Payload.newBuilder()
													           .setNodeDiscovery(
													           MessageProto.NodeDiscovery.newBuilder().
												setNodeDiscoveryMessageType(MessageProto.NodeDiscovery.NodeDiscoveryMessageType.REQUEST_CONNECTION)
												.setNODEID(GlobalConfiguration.getCurrentNode().getNodeID())
												.setNODEIP(GlobalConfiguration.getCurrentNode().getNodeIP())
												.setNODEPORT(GlobalConfiguration.getCurrentNode().getNodePort())
												)).build();
	}
	
		
	
}
