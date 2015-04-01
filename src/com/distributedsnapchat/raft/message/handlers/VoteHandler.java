package com.distributedsnapchat.raft.message.handlers;

import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.buffers.VoteBuffer;
import com.distributedsnapchat.communication.nodes.receiver.MessageDecoder;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class VoteHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{

		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		System.out.println("Inside VoteHandler "+node);
		
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
