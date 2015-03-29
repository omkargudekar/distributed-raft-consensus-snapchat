package com.distsnapchat.communication.buffers;

import java.util.ArrayList;

import com.distsnapchat.beans.Node;

public class CandidacyBuffer
{
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	
	
	public static int getNodeCount()
	{
		return nodes.size();
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
