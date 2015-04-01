package com.distributedsnapchat.raft.message.handlers;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.nodes.receiver.MessageDecoder;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class NominationHandler implements Handler
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
