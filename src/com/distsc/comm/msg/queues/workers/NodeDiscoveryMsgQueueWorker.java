package com.distsc.comm.msg.queues.workers;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.NodeDiscoveryMsgQueue;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NetworkContextMap;
import com.distsc.server.listener.ListnerConnectionRequestQueue;

public class NodeDiscoveryMsgQueueWorker implements Runnable
{
	public void run()
	{
		RequestContext requestMessage = null;
		while (true)
		{
			if (NodeDiscoveryMsgQueue.getCount() > 0)
			{
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
		}
	}
	public void checkAndAddNodeChannel(RequestContext requestMessage)
	{
		Request request=requestMessage.getRequest();
		if(!NetworkContextMap.isChannelExist(request.getPayload().getNodeDiscovery().getNODEID()))
		{
			NetworkContextMap.addNodeChnnelContext(request.getPayload().getNodeDiscovery().getNODEID(),
					requestMessage.getContext());
		}
	}
	
	public void checkAndRemoveNodeChannel(RequestContext requestMessage)
	{
		Request request=requestMessage.getRequest();
		if(NetworkContextMap.isChannelExist(request.getPayload().getNodeDiscovery().getNODEID()))
		{
			NetworkContextMap.removeNodeChannelContext(request.getPayload().getNodeDiscovery().getNODEID());
		}
		
	}
	
	
	
	
	
}
