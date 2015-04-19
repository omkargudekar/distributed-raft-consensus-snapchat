package com.distsc.app;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.distsc.app.config.ConfigReader;
import com.distsc.comm.msg.queues.workers.ClusterThreadPool;
import com.distsc.intercluster.worker.InterClusterThreadPool;

public class RunMain
{
	static Logger logger = LoggerFactory.getLogger(RunMain.class);
	public static void main(String args[])
	{
		
		logger.info("Starting Application");
		if(args[0]==null || args[0].trim().equals(""))
		{
			logger.error("Configuration file parameter missing. Program Terminating");
			System.exit(0);
		}
		else
		{
				logger.info("Reading Configuration File "+ args[0]);
				ConfigReader.setUp(args[0]);
				logger.info("Starting ClusterThreadPool");
				new Thread(new ClusterThreadPool()).start();
				logger.info("Starting InterClusterThreadPool");
			//	new Thread(new InterClusterThreadPool()).start();
				
				
		}		
	}
	
}
