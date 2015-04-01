package com.distributedsnapchat.communication.buffers;

import java.util.ArrayList;

import com.distributedsnapchat.communication.protobuf.ClientMessageProto.ClientMessage;

public class ClientMessageBuffer
{
		private static ArrayList<ClientMessage> recMessages = new ArrayList<ClientMessage>();
		
		
		public static int getMessageCount()
		{
			return recMessages.size();
		}
		public static ClientMessage popMessage()
		{
			if(getMessageCount()>0)
			{
				ClientMessage message=recMessages.get(0);
				recMessages.remove(0);
				return message;
			}

			return null;
			
		}
		
		public static ArrayList<ClientMessage> popMessages()
		{
			if(getMessageCount()>0)
			{

				return recMessages;
			}

			return null;
			
		}
		
		public static void pushMessage(ClientMessage message)
		{
				recMessages.add(message);
			
		}
		
		public static void reset()
		{
				recMessages = new ArrayList<ClientMessage>();
			
		}
		
		

}
