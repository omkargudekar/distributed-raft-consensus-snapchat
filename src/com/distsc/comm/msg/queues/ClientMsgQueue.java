package com.distsc.comm.msg.queues;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;
import com.distsc.intercluster.msg.queue.ClusterRequestContext;

public class ClientMsgQueue
{
	static Logger logger = LoggerFactory.getLogger(ClientMsgQueue.class);

	private static ArrayList<RequestContext> queue = new ArrayList<RequestContext>();
	private static ArrayList<ClusterRequestContext> queue2 = new ArrayList<ClusterRequestContext>();

	
	public static int getCount()
	{
		return queue.size();
	}
	
	
	public static void pushClusterClientRequest(ClusterRequestContext request)
	{
		logger.debug("Pushing Request to ClientMessageMsgQueue : "+ request.getRequest());

		queue2.add(request);
	}
	
	public static ClusterRequestContext popClusterClientRequest()
	{
		if(getCount()>0)
		{
			ClusterRequestContext request=queue2.get(0);
			queue.remove(0);
			logger.debug("Request  Popped from ClientMessageMsgQueue : "+ request.getRequest());
			return request;

		}
		return null;
	}
	public static void push(RequestContext request)
	{
		logger.debug("Pushing Request to ClientMessageMsgQueue : "+ request.getRequest());

		queue.add(request);
	}
	public static RequestContext pop()
	{
		if(getCount()>0)
		{
			RequestContext request=queue.get(0);
			queue.remove(0);
			logger.debug("Request  Popped from ClientMessageMsgQueue : "+ request.getRequest());

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
