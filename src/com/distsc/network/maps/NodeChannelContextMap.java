package com.distsc.network.maps;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeChannelContextMap
{
	static Logger logger = LoggerFactory.getLogger(NodeChannelContextMap.class);

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
		logger.info("Node Channel Added for : "+nodeId);
		contextMap.put(nodeId, nodeChannelContext);
		
	}
	
	public static void removeNodeChannelContext(String nodeId)
	{
		logger.info("Node Channel Removed for : "+nodeId);

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
