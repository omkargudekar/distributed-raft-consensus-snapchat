package com.distsnapchat.communication.buffers;

import java.util.ArrayList;

public class VoteBuffer
{
		private static ArrayList<String> recMessages = new ArrayList<String>();
		
		
		public static int getVoteCount()
		{
			return recMessages.size();
		}
		public static String popVote()
		{
			if(getVoteCount()>0)
			{
				String message=recMessages.get(0);
				recMessages.remove(0);
				return message;
			}

			return null;
			
		}
		
		public static ArrayList<String> popVotes()
		{
			if(getVoteCount()>0)
			{

				return recMessages;
			}

			return null;
			
		}
		
		public static void pushVote(String message)
		{
				recMessages.add(message);
			
		}
		
		public static void reset()
		{
				recMessages = new ArrayList<String>();
			
		}
		
		

}
