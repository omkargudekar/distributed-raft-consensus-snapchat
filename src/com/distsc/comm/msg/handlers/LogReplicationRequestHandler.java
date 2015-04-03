package com.distsc.comm.msg.handlers;


import com.distsc.comm.msg.queues.inbound.LogReplicationRequestBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

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
