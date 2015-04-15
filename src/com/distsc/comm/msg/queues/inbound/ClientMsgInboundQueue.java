package com.distsc.comm.msg.queues.inbound;

import java.util.ArrayList;

import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg;

public class ClientMsgInboundQueue
{
	private static ArrayList<ClientMsg> recMessages = new ArrayList<ClientMsg>();
	
	
	public static int getMessageCount()
	{
		return recMessages.size();
	}
	public static ClientMsg popMessage()
	{
		if(getMessageCount()>0)
		{
			ClientMsg message=recMessages.get(0);
			recMessages.remove(0);
			return message;
		}

		return null;
		
	}
	
	public static ArrayList<ClientMsg> popMessages()
	{
		if(getMessageCount()>0)
		{

			return recMessages;
		}

		return null;
		
	}
	
	public static void pushMessage(ClientMsg message)
	{
			recMessages.add(message);
		
	}
	
	public static void reset()
	{
			recMessages = new ArrayList<ClientMsg>();
		
	}
	
	

}
