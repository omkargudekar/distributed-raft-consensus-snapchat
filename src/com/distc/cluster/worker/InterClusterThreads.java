package com.distc.cluster.worker;

import java.util.ArrayList;

import com.distc.cluster.config.ClusterConfiguration;
import com.distc.cluster.discovery.ClusterDiscoveryThread;
import com.distc.cluster.server.ClusterNode;
import com.distc.cluster.server.ClusterServer;
public class InterClusterThreads implements Runnable
{

	@Override
	public void run()
	{
		ClusterConfiguration.setClusterServerPort(9760);
		ArrayList<ClusterNode> clusterNode=new ArrayList<ClusterNode>();
		clusterNode.add(new ClusterNode("1","192.168.0.39",5570));
		ClusterConfiguration.setNodes(clusterNode);
		ClusterConfiguration.setCurrentClusterNode(new ClusterNode("2","192.168.0.20",9760));
		new Thread(new ClusterServer()).start();
		new Thread(new ClusterDiscoveryThread()).start();

		
	}

}
