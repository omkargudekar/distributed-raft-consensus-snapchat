package com.distc.cluster.discovery;

import io.netty.channel.ChannelHandlerContext;

import com.distc.cluster.msg.queue.ClusterDiscoveryQueue;
import com.distc.cluster.msg.queue.ClusterRequestContext;
import com.distc.cluster.msg.queue.ClusterRequestQueue;
import com.distc.cluster.proto.App.Request;
import com.distc.cluster.server.listener.ClusterListnerConnectionRequestQueue;
import com.distsc.network.maps.NodeChannelContextMap;


public class ClusterDiscoveryMsgQueueWorker implements Runnable
{
	public void run()
	{
		System.out.println("ClusterDiscoveryMsgQueueWorker Thread Started");

		ClusterRequestContext requestMessage = null;
		while (true)
		{
			if (ClusterDiscoveryQueue.getCount() > 0)
			{
					requestMessage = ClusterRequestQueue.pop();
					switch (requestMessage.getRequest().getBody().getNodeDiscovery().getNodeDiscoveryMessageType())
					{
					case REQUEST_CONNECTION:
						ClusterListnerConnectionRequestQueue.push(requestMessage);
						break;

					case RESPONSE_CONNECTION_ACCEPTED:
						checkAndAddClusterNodeChannel(requestMessage);
						break;

					case RESPONSE_CONNECTION_REJECTED:
						checkAndRemoveClusterNodeChannel(requestMessage);
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

	public void checkAndAddClusterNodeChannel(ClusterRequestContext requestMessage)
	{
		Request request = requestMessage.getRequest();
		if (!NodeChannelContextMap.isChannelExist(request.getBody().getNodeDiscovery().getNODEID()))
		{
			System.out.println("Adding Channel for " + request.getBody().getNodeDiscovery().getNODEID());
			NodeChannelContextMap.addNodeChnnelContext(request.getBody().getNodeDiscovery().getNODEID(), requestMessage.getContext());
		}
		else if(NodeChannelContextMap.isChannelExist(request.getBody().getNodeDiscovery().getNODEID()))
		{
			if(isChannelActive(NodeChannelContextMap.getNodeContext((request.getBody().getNodeDiscovery().getNODEID())))==false)
			{
				checkAndRemoveClusterNodeChannel(requestMessage);
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
	public void checkAndRemoveClusterNodeChannel(ClusterRequestContext requestMessage)
	{
		Request request = requestMessage.getRequest();
		if (NodeChannelContextMap.isChannelExist(request.getBody().getNodeDiscovery().getNODEID()))
		{
			System.out.println("Removing Channel of " + request.getBody().getNodeDiscovery().getNODEID());

			NodeChannelContextMap.removeNodeChannelContext(request.getBody().getNodeDiscovery().getNODEID());
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
