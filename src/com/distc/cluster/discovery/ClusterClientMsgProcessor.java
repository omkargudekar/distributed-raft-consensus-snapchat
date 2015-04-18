package com.distc.cluster.discovery;

import com.distc.cluster.msg.queue.ClusterRequestContext;

public class ClusterClientMsgProcessor implements Runnable
{
	ClusterRequestContext clusterRequestContext;
	public ClusterClientMsgProcessor(ClusterRequestContext clusterContext)
	{
		clusterContext=clusterRequestContext;
	}
	@Override
	public void run()
	{

			System.out.println(clusterRequestContext.getRequest().getBody().getClientMessage().getSenderUserName());
		    
	}

}
