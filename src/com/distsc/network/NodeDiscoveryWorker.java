package com.distsc.network;

import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryWorker implements Runnable
{

	public void run()
	{
		Request requestMessage = null;
		while (true)
		{
			if (NetworkDiscoveryQueue.getCount() > 0)
			{
				requestMessage = NetworkDiscoveryQueue.pop();
				switch (requestMessage.getPayload().getNodeDiscovery().getNodeDiscoveryMessageType())
				{
				case RESPONSE_CONNECTION_ACCEPTED:
					System.out.println("Connection Accpted By : " + requestMessage.getPayload().getNodeDiscovery().getNODEID());
					break;

				case RESPONSE_CONNECTION_REJECTED:
					System.out.println("Connection Rejected By : " + requestMessage.getPayload().getNodeDiscovery().getNODEID());
					break;

				default:
					System.out.println("Unknown Node Discovery Message Rejected");
					break;

				}
			}
		}
	}

}
