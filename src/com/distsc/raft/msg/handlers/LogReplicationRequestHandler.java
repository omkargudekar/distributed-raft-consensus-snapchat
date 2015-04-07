package com.distsc.raft.msg.handlers;


import com.distsc.comm.msg.queues.inbound.LogReplicationRequestQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

public class LogReplicationRequestHandler implements RAFTMsgHandler
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


		case Follower:
			System.out.println("Received Log Replication Request..");
			LogReplicationRequestQueue.pushMessage(msg);
			break;
			
		default:
			break;
		}		

	}



}
