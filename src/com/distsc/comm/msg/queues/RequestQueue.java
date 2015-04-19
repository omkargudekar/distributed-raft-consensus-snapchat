package com.distsc.comm.msg.queues;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;

public class RequestQueue
{
	private static ArrayList<RequestContext> queue = new ArrayList<RequestContext>();
	static Logger logger = LoggerFactory.getLogger(RequestQueue.class);

	
	public static int getCount()
	{
		return queue.size();
	}
	
	public static void push(RequestContext request)
	{
		logger.debug("Pushing Request to RequestQueue : "+ request);

		queue.add(request);
	}
	public static RequestContext pop()
	{
		if(getCount()>0)
		{
			RequestContext request=queue.get(0);
			queue.remove(0);
			logger.debug("Popping Message From RequestQueue : "+ request);
			return request;
		}

		return null;
		
	}
	
	public static ArrayList<RequestContext> popAll()
	{
		if(getCount()>0)
		{

			return queue;
		}

		return null;
		
	}
	

	
	public static void reset()
	{
			queue = new ArrayList<RequestContext>();
		
	}
	
	

}
