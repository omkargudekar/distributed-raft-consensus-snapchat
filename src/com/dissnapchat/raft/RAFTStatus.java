package com.dissnapchat.raft;

import java.util.ArrayList;
import java.util.Random;

import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.buffers.NominationsBuffer;
import com.distsnapchat.communication.buffers.VoteBuffer;

public class RAFTStatus
{
	
	public static enum NodeState 
	{
	    Follower, Candidate, Leader,OrphanFollower
	}
	private static NodeState currentNodeState = NodeState.Follower;
	private static Node currentNode=null;
	private static ArrayList<Node> nodes=new ArrayList<Node>();
	private static Node declaredLeader=null;
	private static int raftTimer=3600;
	private static int heartBeatFrequency=1000;
	private static boolean voted=false;
	
	

	
	
	public static synchronized boolean isVoted()
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


	


	public static synchronized Node getCurrentNode()
	{
		return currentNode;
	}

	public static synchronized void setCurrentNode(Node currentNode)
	{
		RAFTStatus.currentNode = currentNode;
	}

	
	public static synchronized void afterLeaderElectinReset()
	{
		VoteBuffer.reset();
		NominationsBuffer.reset();
		
	}
	
	public static synchronized void afterHeartbeatMissedReset()
	{
		declaredLeader=null;

	}
	
	
	
	

	public static synchronized Node getDeclaredLeader()
	{
		return declaredLeader;
	}

	public static synchronized void setDeclaredLeader(Node declaredLeader)
	{
		RAFTStatus.declaredLeader = declaredLeader;
	}
	

	public static synchronized int getHeartBeatFrequency()
	{
		return heartBeatFrequency;
	}


	public static synchronized void setHeartBeatFrequency(int heartBeatFrequency)
	{
		RAFTStatus.heartBeatFrequency = heartBeatFrequency;
	}






	public static synchronized ArrayList<Node> getNodes()
	{
		return nodes;
	}


	public static synchronized void setNodes(ArrayList<Node> nodes)
	{
		RAFTStatus.nodes = nodes;
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




	public static synchronized int getNetwotkSize()
	{
		return nodes.size();
	}


	
	public static synchronized void raftTimerInit() 
	{

	    Random rand = new Random();

	    int randomNum = rand.nextInt((6000 - 4500) + 1) + 4500;
	    
	    setRaftTimer(randomNum);
	}
	
}
