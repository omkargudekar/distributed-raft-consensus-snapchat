package com.distsc.comm.msg.queues.inbound;

import java.util.ArrayList;

import com.distsc.beans.Node;

public class HeartbeatBuffer
{
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	public static int getNodeCount()
	{
		return nodes.size();
	}
	public static Node popNode()
	{
		if(getNodeCount()>0)
		{
			Node node=nodes.get(0);
			nodes.remove(0);
			return node;
		}

		return null;
		
	}
	
	public static ArrayList<Node> popNodes()
	{
		if(getNodeCount()>0)
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
