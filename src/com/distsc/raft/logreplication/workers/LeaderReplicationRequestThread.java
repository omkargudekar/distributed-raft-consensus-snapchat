package com.distsc.raft.logreplication.workers;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.msg.protobuf.NodeMessageProto;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message.MessageType;
import com.distsc.comm.msg.queues.inbound.ClientMessageQueue;
import com.distsc.comm.outbound.OutboundMulticast;
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

			case Follower:
				break;

			default:
				break;
			}
		}
	}
	
	public void checkClientMessageBuffer()
	{
		if(ClientMessageQueue.getMessageCount()>0)
		{
			while(ClientMessageQueue.getMessageCount()>0)
			{
				Message clientMessage=ClientMessageQueue.popMessage();
				OutboundMulticast multicast = new OutboundMulticast();
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
