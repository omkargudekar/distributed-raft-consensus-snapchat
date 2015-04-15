package com.distsc.raft;

import java.util.Random;

import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;

public class RAFTStatus
{
	
	public static enum NodeState 
	{
	    Follower, Candidate, Leader
	}
	private static NodeState currentNodeState = NodeState.Follower;
	private static int totalVotes;
	public static int getTotalVotes()
	{
		return totalVotes;
	}

	public static void setTotalVotes(int totalVotes)
	{
		RAFTStatus.totalVotes = totalVotes;
	}






	private static int currentTerm;
	
	

	



	public static int getCurrentTerm()
	{
		return currentTerm;
	}

	public static void setCurrentTerm(int currentTerm)
	{
		RAFTStatus.currentTerm = currentTerm;
	}






	private static String declaredLeader=null;
	private static int raftTimer=3600;
	private static int heartBeatFrequency=1000;
	private static boolean voted=false;
	


	public static synchronized boolean hasVoted()
	{
		return voted;
	}

	public static synchronized void setVoted(boolean voted)
	{
		RAFTStatus.voted = voted;
	}

	public static synchronized NodeState getCurrentNodeState()
	{
		return currentNodeState;
	}

	public static synchronized void setCurrentNodeState(NodeState currentNodeState)
	{
		System.out.println("Changing Node State From "+RAFTStatus.currentNodeState +" to "+currentNodeState);
		RAFTStatus.currentNodeState = currentNodeState;
	}


	



	
	public static synchronized void afterLeaderElectinReset()
	{
		RequestVoteMsgQueue.reset();
		RequestVoteResultMsgQueue.reset();
		
	}
	
	public static synchronized void afterHeartbeatMissedReset()
	{
		declaredLeader=null;

	}
	
	
	
	

	public static synchronized String getDeclaredLeader()
	{
		return RAFTStatus.declaredLeader;
	}

	public static synchronized void setDeclaredLeader(String nodeId)
	{
		RAFTStatus.declaredLeader = nodeId;
	}
	

	public static synchronized int getHeartBeatFrequency()
	{
		return heartBeatFrequency;
	}


	public static synchronized void setHeartBeatFrequency(int heartBeatFrequency)
	{
		RAFTStatus.heartBeatFrequency = heartBeatFrequency;
	}






	
	public static synchronized boolean isLeaderElected()
	{
		if(getDeclaredLeader()==null)
		{
			return false;
		}
		return true;
	}

	public static synchronized int getRaftTimer()
	{
		return raftTimer;
	}


	public static synchronized void setRaftTimer(int raftTimer)
	{
		RAFTStatus.raftTimer = raftTimer;
	}

	public static void reset()
	{
		RAFTStatus.setVoted(false);
		AppendEntriesQueue.reset();
		AppendEntriesResultQueue.reset();
		RequestVoteMsgQueue.reset();
		RequestVoteResultMsgQueue.reset();
	}





	
	public static synchronized void raftTimerInit() 
	{

	    Random rand = new Random();

	    int randomNum = rand.nextInt((5000 - 3600) + 1) + 3600;
	    
	    setRaftTimer(randomNum);
	}
	
}
