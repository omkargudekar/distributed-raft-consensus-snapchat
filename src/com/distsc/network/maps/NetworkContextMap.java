package com.distsc.network.maps;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class NetworkContextMap
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	
	public static void getSize()
	{
		contextMap.size();
	}
	
	public static HashMap<String,ChannelHandlerContext> getNetworkMap()
	{
		return contextMap;
	}
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
