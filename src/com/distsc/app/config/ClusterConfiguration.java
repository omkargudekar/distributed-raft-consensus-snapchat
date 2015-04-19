package com.distsc.app.config;

import java.util.ArrayList;

import com.distsc.intercluster.server.ClusterNode;


public class ClusterConfiguration
{
	private static ArrayList<ClusterNode> clusters=new ArrayList<ClusterNode>();


	public static ArrayList<ClusterNode> getNodes()
	{
		return clusters;
	}

	public static void setInterClusterNodes(ArrayList<ClusterNode> nodes)
	{
		ClusterConfiguration.clusters = nodes;
	}
}
