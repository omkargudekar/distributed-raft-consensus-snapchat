package com.distsc.comm.msg.queues.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.ListnerConnectionRequestQueue;
import com.distsc.comm.msg.queues.NodeDiscoveryMsgQueue;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;

public class NodeDiscoveryQueueWorker implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(NodeDiscoveryQueueWorker.class);

	public void run()
	{
		logger.info("NodeDiscoveryMsgQueueWorker Thread Started");

		RequestContext requestMessage = null;
		while (true)
		{
			if (NodeDiscoveryMsgQueue.getCount() > 0)
			{
					logger.info("Node Discovery Message Found");
					requestMessage = NodeDiscoveryMsgQueue.pop();
					switch (requestMessage.getRequest().getPayload().getNodeDiscovery().getNodeDiscoveryMessageType())
					{
					case REQUEST_CONNECTION:
						ListnerConnectionRequestQueue.push(requestMessage);
						break;

					case RESPONSE_CONNECTION_ACCEPTED:
						checkAndAddNodeChannel(requestMessage);
						break;

					case RESPONSE_CONNECTION_REJECTED:
						checkAndRemoveNodeChannel(requestMessage);
						break;

					default:
						break;
					}
				
			}
			else
			{
				pause();
			}
		}
	}

	public void checkAndAddNodeChannel(RequestContext requestMessage)
	{
		Request request = requestMessage.getRequest();
		if (!NodeChannelContextMap.isChannelExist(request.getPayload().getNodeDiscovery().getNODEID()))
		{
			logger.info("Adding Channel for " + request.getPayload().getNodeDiscovery().getNODEID());
			NodeChannelContextMap.addNodeChnnelContext(request.getPayload().getNodeDiscovery().getNODEID(), requestMessage.getContext());
		}
		
	}

	
	public void checkAndRemoveNodeChannel(RequestContext requestMessage)
	{
		Request request = requestMessage.getRequest();
		if (NodeChannelContextMap.isChannelExist(request.getPayload().getNodeDiscovery().getNODEID()))
		{
			System.out.println("Removing Channel of " + request.getPayload().getNodeDiscovery().getNODEID());

			NodeChannelContextMap.removeNodeChannelContext(request.getPayload().getNodeDiscovery().getNODEID());
		}

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
