package com.distsc.intercluster.workers;

import com.distsc.intercluster.msg.handlers.ClusterMsgHandler;
import com.distsc.intercluster.msg.queues.inbound.InboundInterClusterMsgQueue;

public class InboundWorkerThread implements Runnable
{

	@Override
	public void run()
	{
		while(true)
		{
			if(InboundInterClusterMsgQueue.getMessageCount()>0)
			{
				new ClusterMsgHandler().handle(InboundInterClusterMsgQueue.popMessage());
				
				
			}
		}
	}
	
}
