package com.distsc.intercluster.config;

import java.util.ArrayList;

import com.distsc.intercluster.server.ClusterNode;


public class ClusterConfiguration
{
	private static ArrayList<ClusterNode> nodes=new ArrayList<ClusterNode>();
	private static ClusterNode currentClusterNode;

	private static int clusterServerPort;

	public static int getClusterServerPort()
	{
		return clusterServerPort;
	}

	public static void setClusterServerPort(int clusterServerPort)
	{
		ClusterConfiguration.clusterServerPort = clusterServerPort;
	}

	public static ClusterNode getCurrentClusterNode()
	{
		return currentClusterNode;
	}

	public static void setCurrentClusterNode(ClusterNode currentClusterNode)
	{
		ClusterConfiguration.currentClusterNode = currentClusterNode;
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
