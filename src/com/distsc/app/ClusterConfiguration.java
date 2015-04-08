package com.distsc.app;

import java.util.ArrayList;

import com.distsc.beans.Node;

public class ClusterConfiguration
{
	private static ArrayList<Node> nodes=new ArrayList<Node>();

	public static ArrayList<Node> getNodes()
	{
		return nodes;
	}

	public static void setNodes(ArrayList<Node> nodes)
	{
		ClusterConfiguration.nodes = nodes;
	}

}
