package com.distsc.comm.msg.queues.inbound;

import java.util.ArrayList;

import com.distsc.beans.Node;
import com.distsc.raft.RAFTStatus;

public class VoteBuffer
{
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	
	public static int getNodeVoteCount()
	{
		if(RAFTStatus.getCurrentNodeState()==RAFTStatus.NodeState.Candidate)
		{
			return nodes.size()+1;
		}
		return 0;
	}
	public static Node popNode()
	{
		if(getNodeVoteCount()>0)
		{
			Node node=nodes.get(0);
			nodes.remove(0);
			return node;
		}

		return null;
		
	}
	
	public static ArrayList<Node> popNodes()
	{
		if(getNodeVoteCount()>0)
		{

			return nodes;
		}

		return null;
		
	}
	
	public static void pushNode(Node node)
	{
			nodes.add(node);
		
	}
	
	public static void reset()
	{
			nodes = new ArrayList<Node>();
		
	}

}
