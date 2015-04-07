package com.distsc.comm.msg.queues.outbound;

import java.util.ArrayList;

import com.distsc.comm.protobuf.ClientMessage.ClientMsg;

public class OuboundClientQueue 
{
private static ArrayList<ClientMsg> outgoingMessages = new ArrayList<ClientMsg>();
	
	
	public static int getMessageCount()
	{
		return outgoingMessages.size();
	}
	public static ClientMsg popMessage()
	{
		if(getMessageCount()>0)
		{
			ClientMsg packet=outgoingMessages.get(0);
			outgoingMessages.remove(0);
			return packet;
		}

		return null;
		
	}
	
	public static ArrayList<ClientMsg> popMessages()
	{
		if(getMessageCount()>0)
		{

			return outgoingMessages;
		}

		return null;
		
	}
	
	public static void pushMessage(ClientMsg packet)
	{
		System.out.println("Message stored in outbound queue");
		outgoingMessages.add(packet);
		
	}
	
	public static void reset()
	{
		outgoingMessages = new ArrayList<ClientMsg>();
		
	}
	
	

}
