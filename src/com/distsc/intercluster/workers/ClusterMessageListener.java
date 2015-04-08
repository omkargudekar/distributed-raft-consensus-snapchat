package com.distsc.intercluster.workers;

import com.distsc.intercluster.queues.inbound.ClusterMessageQueue;

public class ClusterMessageListener implements Runnable
{

	@Override
	public void run()
	{
		while(true)
		{
			if(ClusterMessageQueue.getMessageCount()>0)
			{
				
				
				
			}
		}
	}
	
}
