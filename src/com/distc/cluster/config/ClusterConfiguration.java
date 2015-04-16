package com.distc.cluster.config;

import java.util.ArrayList;

import com.distc.cluster.server.listener.ClusterNode;

public class ClusterConfiguration
{
	private static ArrayList<ClusterNode> nodes=new ArrayList<ClusterNode>();
	private static ClusterNode clusterNode;

	public static ClusterNode getClusterNode()
	{
		return clusterNode;
	}

	public static void setClusterNode(ClusterNode clusterNode)
	{
		ClusterConfiguration.clusterNode = clusterNode;
	}

	public static ArrayList<ClusterNode> getNodes()
	{
		return nodes;
	}

	public static void setNodes(ArrayList<ClusterNode> nodes)
	{
		ClusterConfiguration.nodes = nodes;
	}
}
