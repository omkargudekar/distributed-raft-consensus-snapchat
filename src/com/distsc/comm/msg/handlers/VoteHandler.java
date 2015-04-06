package com.distsc.comm.msg.handlers;

import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.VoteBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

public class VoteHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{

		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		
		switch (RAFTStatus.getCurrentNodeState())
		{
			case Leader:
			break;

			case Candidate:
			VoteBuffer.pushNode(node);
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
