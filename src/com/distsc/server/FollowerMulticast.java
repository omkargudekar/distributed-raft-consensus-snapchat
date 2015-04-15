package com.distsc.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NetworkContextMap;
public class FollowerMulticast
{

	public void send(Request request)
	{
		HashMap<String,ChannelHandlerContext> contextMap=NetworkContextMap.getNetworkMap();
		
		for (ChannelHandlerContext requestContext : contextMap.values()) 
		{
			requestContext.writeAndFlush(request);
		
		}
	
	}
	
}
