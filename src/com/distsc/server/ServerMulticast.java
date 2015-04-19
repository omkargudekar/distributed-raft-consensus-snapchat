package com.distsc.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
public class ServerMulticast
{

	static Logger logger = LoggerFactory.getLogger(ServerMulticast.class);

	public void send(Request request)
	{
		
		logger.info("Mutlicasting : "+request);
		HashMap<String,ChannelHandlerContext> contextMap=NodeChannelContextMap.getNetworkMap();
		
		for (ChannelHandlerContext requestContext : contextMap.values()) 
		{
			requestContext.writeAndFlush(request);
		
		}
	
	}
	
}
