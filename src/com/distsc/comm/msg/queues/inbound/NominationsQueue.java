package com.distsc.comm.msg.queues.inbound;

import java.util.ArrayList;

import com.distsc.beans.Node;

public class NominationsQueue
{
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	
	
	public static int getNodeCount()
	{
		return nodes.size();
	}
	
	public static void pushCandidate(Node node)
	{
		nodes.add(node);
	}
	public static Node popCandidate()
	{
		if(getNodeCount()>0)
		{
			Node node=nodes.get(0);
			nodes.remove(0);
			return node;
		}

		return null;
		
	}
	
	public static ArrayList<Node> popCandidates()
	{
		if(getNodeCount()>0)
		{

			return nodes;
		}

		return null;
		
	}
	

	
	public static void reset()
	{
			nodes = new ArrayList<Node>();
		
	}
	
	

}
