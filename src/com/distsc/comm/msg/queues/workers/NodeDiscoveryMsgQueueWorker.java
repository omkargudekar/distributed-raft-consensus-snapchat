package com.distsc.comm.msg.queues.workers;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.beans.RequestContext;
import com.distsc.comm.msg.queues.NodeDiscoveryMsgQueue;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.NodeChannelContextMap;
import com.distsc.server.listener.ListnerConnectionRequestQueue;

public class NodeDiscoveryMsgQueueWorker implements Runnable
{
	public void run()
	{
		System.out.println("NodeDiscoveryMsgQueueWorker Thread Started");

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
			System.out.println("Adding Channel for " + request.getPayload().getNodeDiscovery().getNODEID());
			NodeChannelContextMap.addNodeChnnelContext(request.getPayload().getNodeDiscovery().getNODEID(), requestMessage.getContext());
		}
		else if(NodeChannelContextMap.isChannelExist(request.getPayload().getNodeDiscovery().getNODEID()))
		{
			if(isChannelActive(NodeChannelContextMap.getNodeContext((request.getPayload().getNodeDiscovery().getNODEID())))==false)
			{
				checkAndRemoveNodeChannel(requestMessage);
			}
		}
			
	}

	public boolean isChannelActive(ChannelHandlerContext ctx)
	{
		boolean writable=false;
		try
		{
			ctx.write("Test");
			writable=true;
		}
		catch(Exception e)
		{
			writable=false;

		}
		
		return writable;
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
