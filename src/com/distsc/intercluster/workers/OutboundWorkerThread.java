package com.distsc.intercluster.workers;

import com.distsc.intercluster.msg.queues.inbound.InboundClusterMsgQueue;

public class OutboundWorkerThread implements Runnable
{

	@Override
	public void run()
	{
		while(true)
		{
			if(InboundClusterMsgQueue.getMessageCount()>0)
			{
				
				
				
			}
			else
			{
				pause();
			}
		}
	}
	public void pause()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
