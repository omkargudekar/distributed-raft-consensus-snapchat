package com.distsc.network;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class NetworkMap
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	public static ChannelHandlerContext getNodeContext(String nodeId)
	{
		
		return contextMap.get(nodeId); 
	}
	
	public static void addNodeChnnelContext(String nodeId,ChannelHandlerContext nodeChannelContext)
	{
		contextMap.put(nodeId, nodeChannelContext);
		
	}
	
	public static void removeNodeChannelContext(String nodeId)
	{
		contextMap.remove(nodeId);
		
	}
	public static boolean isChannelExist(String nodeId)
	{
		if(contextMap.containsKey(nodeId))
		{
			return true;
		}
		return false;
	}
	
}
