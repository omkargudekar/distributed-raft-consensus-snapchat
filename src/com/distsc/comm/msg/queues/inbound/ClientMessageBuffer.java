package com.distsc.comm.msg.queues.inbound;

import java.util.ArrayList;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class ClientMessageBuffer
{
		private static ArrayList<Message> recMessages = new ArrayList<Message>();
		
		
		public static int getMessageCount()
		{
			return recMessages.size();
		}
		public static Message popMessage()
		{
			if(getMessageCount()>0)
			{
				Message message=recMessages.get(0);
				recMessages.remove(0);
				return message;
			}

			return null;
			
		}
		
		public static ArrayList<Message> popMessages()
		{
			if(getMessageCount()>0)
			{

				return recMessages;
			}

			return null;
			
		}
		
		public static void pushMessage(Message message)
		{
				recMessages.add(message);
			
		}
		
		public static void reset()
		{
				recMessages = new ArrayList<Message>();
			
		}
		
		

}
