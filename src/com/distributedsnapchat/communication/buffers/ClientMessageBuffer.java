package com.distributedsnapchat.communication.buffers;

import java.util.ArrayList;

import com.distributedsnapchat.communication.protobuf.NodeMessageProto;

public class ClientMessageBuffer
{
		private static ArrayList<NodeMessageProto.ClientMessage> recMessages = new ArrayList<NodeMessageProto.ClientMessage>();
		
		
		public static int getMessageCount()
		{
			return recMessages.size();
		}
		public static NodeMessageProto.ClientMessage popMessage()
		{
			if(getMessageCount()>0)
			{
				NodeMessageProto.ClientMessage message=recMessages.get(0);
				recMessages.remove(0);
				return message;
			}

			return null;
			
		}
		
		public static ArrayList<NodeMessageProto.ClientMessage> popMessages()
		{
			if(getMessageCount()>0)
			{

				return recMessages;
			}

			return null;
			
		}
		
		public static void pushMessage(NodeMessageProto.ClientMessage message)
		{
				recMessages.add(message);
			
		}
		
		public static void reset()
		{
				recMessages = new ArrayList<NodeMessageProto.ClientMessage>();
			
		}
		
		

}
