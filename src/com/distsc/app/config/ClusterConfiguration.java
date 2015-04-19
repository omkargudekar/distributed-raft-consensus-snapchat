package com.distsc.app.config;

import java.util.ArrayList;

import com.distsc.intercluster.server.ClusterNode;


public class ClusterConfiguration
{
	private static ArrayList<ClusterNode> clusters=new ArrayList<ClusterNode>();

	private static int clusterServerPort;

	public static int getClusterServerPort()
	{
		return clusterServerPort;
	}

	public static void setClusterServerPort(int clusterServerPort)
	{
		ClusterConfiguration.clusterServerPort = clusterServerPort;
	}

	

	public static ArrayList<ClusterNode> getNodes()
	{
		return clusters;
	}

	public static void setInterClusterNodes(ArrayList<ClusterNode> nodes)
	{
		ClusterConfiguration.clusters = nodes;
	}
}
