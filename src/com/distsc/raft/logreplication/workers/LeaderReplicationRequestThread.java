package com.distsc.raft.logreplication.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.client.ClientMulticast;
import com.distsc.comm.msg.queues.inbound.ClientMessageBuffer;
import com.distsc.comm.protobuf.NodeMessageProto;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.comm.protobuf.NodeMessageProto.Message.MessageType;
import com.distsc.raft.RAFTStatus;

public class LeaderReplicationRequestThread implements Runnable
{
	public void run()
	{
		System.out.println("LeaderReplicationRequestThread Started");
		while (true)
		{

			switch (RAFTStatus.getCurrentNodeState())
			{
			case Leader:
				checkClientMessageBuffer();
				break;

			case Candidate:
				break;

			case OrphanFollower:
				break;

			case Follower:
				break;

			default:
				break;
			}
		}
	}
	
	public void checkClientMessageBuffer()
	{
		if(ClientMessageBuffer.getMessageCount()>0)
		{
			while(ClientMessageBuffer.getMessageCount()>0)
			{
				Message clientMessage=ClientMessageBuffer.popMessage();
				ClientMulticast multicast = new ClientMulticast();
				Message msg = NodeMessageProto.Message.newBuilder().setImageBits(clientMessage.getImageBits())
						.setFileName(clientMessage.getFileName())
						.setMessageType(MessageType.LOG_REPLICATION_REQUEST).
						setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).
						setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).
						setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
				multicast.send(msg);

				
			}
			
		}
	}

	
}
