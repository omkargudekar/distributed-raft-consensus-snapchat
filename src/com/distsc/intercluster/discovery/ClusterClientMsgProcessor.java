package com.distsc.intercluster.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.distsc.comm.msg.queues.ClientMsgQueue;
import com.distsc.intercluster.msg.queue.ClusterRequestContext;

public class ClusterClientMsgProcessor implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(ClusterClientMsgProcessor.class);

	ClusterRequestContext clusterRequestContext=null;
	public ClusterClientMsgProcessor(ClusterRequestContext clusterContext)
	{
		clusterContext=clusterRequestContext;
	}
	@Override
	public void run()
	{

		logger.info("Processing Client Message");
		logger.info(clusterRequestContext.getRequest().getBody().getClientMessage().toString());
		ClientMsgQueue.pushClusterClientRequest(clusterRequestContext);
		    
	}         

}
