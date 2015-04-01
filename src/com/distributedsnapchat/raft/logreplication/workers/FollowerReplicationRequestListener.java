package com.distributedsnapchat.raft.logreplication.workers;

import com.distributedsnapchat.app.GlobalConfiguration;
import com.distributedsnapchat.communication.MulticastMessage;
import com.distributedsnapchat.communication.buffers.ClientMessageBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message.MessageType;
import com.distributedsnapchat.raft.RAFTStatus;

public class FollowerReplicationRequestListener implements Runnable
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
				NodeMessageProto.ClientMessage clientMessage=ClientMessageBuffer.popMessage();
				MulticastMessage multicast = new MulticastMessage();
				Message msg = NodeMessageProto.Message.newBuilder().setClientMessage(clientMessage)
						.setMessageType(MessageType.LOG_REPLICATION_REQUEST).setNodeId(GlobalConfiguration.getCurrentNode().getNodeID()).setNodeIp(GlobalConfiguration.getCurrentNode().getNodeIP()).setNodePort(GlobalConfiguration.getCurrentNode().getNodePort()).build();
				multicast.send(msg);

				
			}
			
		}
	}

}
