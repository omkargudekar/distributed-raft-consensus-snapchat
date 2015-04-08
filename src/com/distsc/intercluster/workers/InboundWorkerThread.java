package com.distsc.intercluster.workers;

import com.distsc.intercluster.msg.queues.inbound.InboundClusterMsgQueue;

public class InboundWorkerThread implements Runnable
{

	@Override
	public void run()
	{
		while(true)
		{
			if(InboundClusterMsgQueue.getMessageCount()>0)
			{
				
				
				
			}
		}
	}
	
}
