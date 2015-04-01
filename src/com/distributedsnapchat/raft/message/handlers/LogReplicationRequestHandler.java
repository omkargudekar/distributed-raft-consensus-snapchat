package com.distributedsnapchat.raft.message.handlers;

import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.buffers.ClientMessageBuffer;
import com.distributedsnapchat.communication.buffers.HeartbeatBuffer;
import com.distributedsnapchat.communication.buffers.LogReplicationRequestBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.ClientMessage;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class LogReplicationRequestHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{


	}

	@Override
	public void handle(ClientMessage msg)
	{		
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			break;

		case Candidate:
			break;

		case OrphanFollower:
			break;

		case Follower:
			LogReplicationRequestBuffer.pushMessage(msg);
			break;
			
		default:
			break;
		}		
	}


}
