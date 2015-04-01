package com.distributedsnapchat.raft.message.handlers;


import com.distributedsnapchat.communication.buffers.LogReplicationRequestBuffer;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class LogReplicationRequestHandler implements Handler
{

	@Override
	public void handle(Message msg)
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
			System.out.println("Received Log Replication Request..");
			LogReplicationRequestBuffer.pushMessage(msg);
			break;
			
		default:
			break;
		}		

	}



}
