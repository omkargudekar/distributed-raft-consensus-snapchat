package com.distsc.comm.msg.queues;

import java.util.ArrayList;
import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryQueue
{
	private static ArrayList<Request> queue = new ArrayList<Request>();
	
	
	public static int getCount()
	{
		return queue.size();
	}
	
	public static void push(Request request)
	{
		queue.add(request);
	}
	public static Request pop()
	{
		if(getCount()>0)
		{
			Request request=queue.get(0);
			queue.remove(0);
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
