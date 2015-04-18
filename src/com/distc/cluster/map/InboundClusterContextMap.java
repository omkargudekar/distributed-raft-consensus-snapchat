package com.distc.cluster.map;

import io.netty.channel.Channel;

import java.util.HashMap;

public class InboundClusterContextMap
{
	private static HashMap<String,Channel> contextMap=new HashMap<String,Channel>();
	
	
	public static void getSize()
	{
		contextMap.size();
	}
	
	public static HashMap<String,Channel> getClusterNetworkMap()
	{
		return contextMap;
	}
	public static Channel getNodeContext(String nodeId)
	{
		
		return contextMap.get(nodeId); 
	}
	
	public static void addClusterContextChnnelContext(String nodeId,Channel nodeChannelContext)
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
