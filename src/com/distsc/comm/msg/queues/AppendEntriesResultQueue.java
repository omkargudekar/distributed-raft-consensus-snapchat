package com.distsc.comm.msg.queues;

import java.util.ArrayList;

import com.distsc.beans.RequestContext;

public class AppendEntriesResultQueue
{
	private static ArrayList<RequestContext> queue = new ArrayList<RequestContext>();
	
	
	public static int getCount()
	{
		return queue.size();
	}
	
	public static void push(RequestContext request)
	{
		queue.add(request);
	}
	public static RequestContext pop()
	{
		if(getCount()>0)
		{
			RequestContext request=queue.get(0);
			queue.remove(0);
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
