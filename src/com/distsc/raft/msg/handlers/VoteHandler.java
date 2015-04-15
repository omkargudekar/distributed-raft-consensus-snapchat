package com.distsc.raft.msg.handlers;

import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.VotesQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

public class VoteHandler implements RAFTMsgHandler
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
			VotesQueue.pushNode(node);
			break;

			case Follower:
			break;

		default:
			break;
		}
		
	}


}
