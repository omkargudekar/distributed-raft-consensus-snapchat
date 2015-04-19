package com.distsc.intercluster.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.intercluster.discovery.ClusterDiscoveryThread;
import com.distsc.intercluster.server.ClusterServer;
public class InterClusterThreadPool implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(InterClusterThreadPool.class);

	@Override
	public void run()
	{
    	logger.info("Starting InterCluster Threads...");

		new Thread(new ClusterServer()).start();
		new Thread(new ClusterDiscoveryThread()).start();
		
	}

}
