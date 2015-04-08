package com.distsc.comm.msg.queues.outbound;

import java.util.ArrayList;

import com.distsc.beans.Packet;


public class OutboundClusterMessageQueue
{

	private static ArrayList<Packet> outgoingMessages = new ArrayList<Packet>();
	
	
	public static int getMessageCount()
	{
		return outgoingMessages.size();
	}
	public static Packet popMessage()
	{
		if(getMessageCount()>0)
		{
			Packet packet=outgoingMessages.get(0);
			outgoingMessages.remove(0);
			return packet;
		}

		return null;
		
	}
	
	public static ArrayList<Packet> popMessages()
	{
		if(getMessageCount()>0)
		{

			return outgoingMessages;
		}

		return null;
		
	}
	
	public static void pushMessage(Packet packet)
	{
		outgoingMessages.add(packet);
		
	}
	
	public static void reset()
	{
		outgoingMessages = new ArrayList<Packet>();
		
	}
	
	
}
