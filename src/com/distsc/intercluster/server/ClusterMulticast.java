package com.distsc.intercluster.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.intercluster.map.OuboundClusterContextMap;
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
