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
	
	
	

	
	
	public static NodeState getCurrentNodeState()
	{
		return currentNodeState;
	}

	public static void setCurrentNodeState(NodeState currentNodeState)
	{
		RAFTStatus.currentNodeState = currentNodeState;
	}


	


	public static Node getCurrentNode()
	{
		return currentNode;
	}

	public static void setCurrentNode(Node currentNode)
	{
		RAFTStatus.currentNode = currentNode;
	}

	
	public static void afterLeaderElectinReset()
	{
		VoteBuffer.reset();
		NominationsBuffer.reset();
		
	}
	
	public static void afterHeartbeatMissedReset()
	{
		declaredLeader=null;

	}
	
	
	
	

	public static Node getDeclaredLeader()
	{
		return declaredLeader;
	}

	public static void setDeclaredLeader(Node declaredLeader)
	{
		RAFTStatus.declaredLeader = declaredLeader;
	}
	

	public static int getHeartBeatFrequency()
	{
		return heartBeatFrequency;
	}


	public static void setHeartBeatFrequency(int heartBeatFrequency)
	{
		RAFTStatus.heartBeatFrequency = heartBeatFrequency;
	}






	public static ArrayList<Node> getNodes()
	{
		return nodes;
	}


	public static void setNodes(ArrayList<Node> nodes)
	{
		RAFTStatus.nodes = nodes;
	}
	
	public static boolean isLeaderElected()
	{
		if(getDeclaredLeader()==null)
		{
			return false;
		}
		return true;
	}

	public static int getRaftTimer()
	{
		return raftTimer;
	}


	public static void setRaftTimer(int raftTimer)
	{
		RAFTStatus.raftTimer = raftTimer;
	}




	public static int getNetwotkSize()
	{
		return nodes.size();
	}


	
	public static void raftTimerInit() 
	{

	    Random rand = new Random();

	    int randomNum = rand.nextInt((6000 - 4500) + 1) + 4500;
	    
	    setRaftTimer(randomNum);
	}
	
}
