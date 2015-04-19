package com.distsc.intercluster.worker;

import java.util.ArrayList;

import com.distsc.app.config.ClusterConfiguration;
import com.distsc.intercluster.discovery.ClusterDiscoveryThread;
import com.distsc.intercluster.server.ClusterNode;
import com.distsc.intercluster.server.ClusterServer;
public class InterClusterThreadPool implements Runnable
{

	@Override
	public void run()
	{
		ClusterConfiguration.setClusterServerPort(9760);
		ArrayList<ClusterNode> clusterNode=new ArrayList<ClusterNode>();
		clusterNode.add(new ClusterNode("1","192.168.0.39",5570));
	
		new Thread(new ClusterServer()).start();
		new Thread(new ClusterDiscoveryThread()).start();

		
	}

}
