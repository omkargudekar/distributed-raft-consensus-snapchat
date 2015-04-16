package com.distc.cluster.msg.queue;

import java.util.ArrayList;

public class ClusterRequestQueue
{
	private static ArrayList<ClusterRequestContext> queue = new ArrayList<ClusterRequestContext>();
	
	
	public static int getCount()
	{
		return queue.size();
	}
	
	public static void push(ClusterRequestContext request)
	{
		queue.add(request);
	}
	public static ClusterRequestContext pop()
	{
		if(getCount()>0)
		{
			ClusterRequestContext request=queue.get(0);
			queue.remove(0);
			return request;
		}

		return null;
		
	}
	
	public static ArrayList<ClusterRequestContext> popAll()
	{
		if(getCount()>0)
		{

			return queue;
		}

		return null;
		
	}
	

	
	public static void reset()
	{
			queue = new ArrayList<ClusterRequestContext>();
		
	}
	
	

}
