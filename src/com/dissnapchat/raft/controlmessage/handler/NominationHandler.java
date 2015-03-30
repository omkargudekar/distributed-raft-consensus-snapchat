package com.dissnapchat.raft.controlmessage.handler;
import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.buffers.NominationsBuffer;
import com.distsnapchat.communication.receiver.MessageDecoder;

public class NominationHandler implements Handler
{

	@Override
	public void handle(String msg)
	{

		Node node=MessageDecoder.extractNodeInformation(msg);
		NominationsBuffer.pushCandidate(node);
		
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
