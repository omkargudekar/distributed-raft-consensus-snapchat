package com.distsnapchat.communication.receiver.buffers;

import java.util.ArrayList;

public class VoteBuffer
{
		private static ArrayList<String> recMessages = new ArrayList<String>();
		
		
		public static int getMessageCount()
		{
			return recMessages.size();
		}
		public static String popMessage()
		{
			if(getMessageCount()>0)
			{
				String message=recMessages.get(0);
				recMessages.remove(0);
				return message;
			}

			return null;
			
		}
		
		public static ArrayList<String> popMessages()
		{
			if(getMessageCount()>0)
			{

				return recMessages;
			}

			return null;
			
		}
		
		public static void pushMessage(String message)
		{
				recMessages.add(message);
			
		}
		
		public static void reset()
		{
				recMessages = new ArrayList<String>();
			
		}
		
		

}
