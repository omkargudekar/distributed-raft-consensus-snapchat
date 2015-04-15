package com.distsc.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
public class ClusterMulticast
{

	public void send(Request request)
	{
		HashMap<String,ChannelHandlerContext> contextMap=NodeChannelContextMap.getNetworkMap();
		
		for (ChannelHandlerContext requestContext : contextMap.values()) 
		{
			requestContext.writeAndFlush(request);
		
		}
	
	}
	
}
