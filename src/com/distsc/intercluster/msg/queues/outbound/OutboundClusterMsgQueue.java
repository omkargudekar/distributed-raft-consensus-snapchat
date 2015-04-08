package com.distsc.intercluster.msg.queues.outbound;

import java.util.ArrayList;

import com.distsc.intercluster.msg.protobuff.ClusterMessageProto.ClusterMessage;


public class OutboundClusterMsgQueue
{
	private static ArrayList<ClusterMessage> recMessages = new ArrayList<ClusterMessage>();
	
	
	public static int getMessageCount()
	{
		return recMessages.size();
	}
	public static ClusterMessage popMessage()
	{
		if(getMessageCount()>0)
		{
			ClusterMessage message=recMessages.get(0);
			recMessages.remove(0);
			return message;
		}

		return null;
		
	}
	
	public static ArrayList<ClusterMessage> popMessages()
	{
		if(getMessageCount()>0)
		{

			return recMessages;
		}

		return null;
		
	}
	
	public static void pushMessage(ClusterMessage message)
	{
			recMessages.add(message);
		
	}
	
	public static void reset()
	{
			recMessages = new ArrayList<ClusterMessage>();
		
	}
	

}
