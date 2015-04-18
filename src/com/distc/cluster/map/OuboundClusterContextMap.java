package com.distc.cluster.map;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class OuboundClusterContextMap
{
	private static HashMap<String,ChannelHandlerContext> contextMap=new HashMap<String,ChannelHandlerContext>();
	
	
	public static void getSize()
	{
		contextMap.size();
	}
	
	public static HashMap<String,ChannelHandlerContext> getClusterNetworkMap()
	{
		return contextMap;
	}
	public static ChannelHandlerContext getNodeContext(String nodeId)
	{
		
		return contextMap.get(nodeId); 
	}
	
	public static void addClusterContextChnnelContext(String nodeId,ChannelHandlerContext nodeChannelContext)
	{
		System.out.println("Channel Added for : "+nodeId);
		contextMap.put(nodeId, nodeChannelContext);
		
	}
	
	public static void removeClusterChannelContext(String nodeId)
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
