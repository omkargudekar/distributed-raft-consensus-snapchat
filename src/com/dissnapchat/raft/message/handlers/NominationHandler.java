package com.dissnapchat.raft.message.handlers;
import org.dissnapchat.protobuf.MessageProto.Message;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.buffers.NominationsBuffer;
import com.distsnapchat.communication.receiver.MessageDecoder;

public class NominationHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		System.out.println("Inside NominationHandler "+node);

		
		switch (RAFTStatus.getCurrentNodeState())
		{
			case Leader:
			break;

			case Candidate:
			break;

			case OrphanFollower:
			NominationsBuffer.pushCandidate(node);
			break;

			case Follower:
			break;

		default:
			break;
		}
	}
	


}
