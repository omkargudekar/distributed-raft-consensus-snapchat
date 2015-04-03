package com.distsc.comm.msg.handlers;
import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.NominationsBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

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
