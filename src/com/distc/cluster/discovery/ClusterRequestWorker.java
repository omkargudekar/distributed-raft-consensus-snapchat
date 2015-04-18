package com.distc.cluster.discovery;


import com.distc.cluster.map.OuboundClusterContextMap;
import com.distc.cluster.msg.queue.ClusterRequestContext;
import com.distc.cluster.msg.queue.ClusterRequestQueue;


public class ClusterRequestWorker implements Runnable
{
	public void run()
	{
		System.out.println("ClusterDiscoveryMsgQueueWorker Thread Started");

		ClusterRequestContext requestMessage = null;
		while (true)
		{
			if (ClusterRequestQueue.getCount() > 0)
			{
				System.out.println("&&&&&&&&&CLuster Message Found");
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
	
			System.out.println("Adding Channel for " + requestMessage.getRequest().getJoinMessage().getFromClusterId());
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
