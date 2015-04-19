package com.distsc.intercluster.discovery;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.intercluster.map.OuboundClusterContextMap;
import com.distsc.intercluster.msg.queue.ClusterRequestContext;
import com.distsc.intercluster.msg.queue.ClusterRequestQueue;


public class ClusterRequestWorker implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(ClusterRequestWorker.class);


	public void run()
	{
		logger.info("ClusterRequestWorker Thread Started");

		ClusterRequestContext requestMessage = null;
		while (true)
		{
			if (ClusterRequestQueue.getCount() > 0)
			{
					logger.info("Processing Cluster Request Found");
					requestMessage = ClusterRequestQueue.pop();
				
					if(requestMessage.getRequest().hasJoinMessage())
					{
						
						addClusterChannel(requestMessage);
					}
					else
					{
						new Thread(new ClusterClientMsgProcessor(requestMessage));
					}									
			}
			else
			{
				pause();
			}
		}
	}

	public void addClusterChannel(ClusterRequestContext requestMessage)
	{
	
			logger.info("Adding Channel for " + requestMessage.getRequest().getJoinMessage().getFromClusterId());
			OuboundClusterContextMap.addClusterContextChnnelContext(Integer.toString(requestMessage.getRequest().getJoinMessage().getFromClusterId()), requestMessage.getContext());
			
	}

	public void pause()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
