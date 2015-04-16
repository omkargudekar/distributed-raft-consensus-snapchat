package com.distc.cluster.worker;

import java.util.ArrayList;

import com.distc.cluster.config.ClusterConfiguration;
import com.distc.cluster.discovery.ClusterDiscoveryThread;
import com.distc.cluster.server.ClusterServer;
import com.distc.cluster.server.listener.ClusterListenerThread;
import com.distc.cluster.server.listener.ClusterNode;

public class InterClusterThreads implements Runnable
{

	@Override
	public void run()
	{
		ArrayList<ClusterNode> clusterNode=new ArrayList<ClusterNode>();
		clusterNode.add(new ClusterNode("1","192.168.0.,14",9760));
		ClusterConfiguration.setNodes(clusterNode);
		new Thread(new ClusterDiscoveryThread()).start();
		new Thread(new ClusterServer()).start();
		new Thread(new ClusterListenerThread()).start();

		
	}

}
