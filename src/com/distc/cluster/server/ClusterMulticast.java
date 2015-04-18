package com.distc.cluster.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import com.distc.cluster.map.OuboundClusterContextMap;
import com.distsc.comm.protobuf.MessageProto.Request;
public class ClusterMulticast
{

	public void send(Request request)
	{
		HashMap<String,ChannelHandlerContext> contextMap=OuboundClusterContextMap.getClusterNetworkMap();
		
		for (ChannelHandlerContext requestContext : contextMap.values()) 
		{
			requestContext.writeAndFlush(request);
		
		}
	
	}
	
}
