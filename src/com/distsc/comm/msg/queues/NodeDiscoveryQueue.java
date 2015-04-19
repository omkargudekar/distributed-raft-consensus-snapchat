package com.distsc.comm.msg.queues;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryQueue
{
	static Logger logger = LoggerFactory.getLogger(NodeDiscoveryQueue.class);
	private static ArrayList<Request> queue = new ArrayList<Request>();
	
	
	public static int getCount()
	{
		return queue.size();
	}
	
	public static void push(Request request)
	{
		logger.debug("Pushing Request to NodeDiscoveryQueue : "+ request);
		queue.add(request);
	}
	public static Request pop()
	{
		if(getCount()>0)
		{
			Request request=queue.get(0);
			queue.remove(0);
			logger.debug("Popping Message From NodeDiscoveryQueue : "+ request);
			return request;
		}

		return null;
		
	}
	
	public static ArrayList<Request> popAll()
	{
		if(getCount()>0)
		{

			return queue;
		}

		return null;
		
	}
	

	
	public static void reset()
	{
			queue = new ArrayList<Request>();
		
	}
	
	

}
