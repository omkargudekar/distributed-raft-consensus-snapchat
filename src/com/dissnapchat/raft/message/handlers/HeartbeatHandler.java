package com.dissnapchat.raft.message.handlers;
import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.buffers.HeartbeatBuffer;
import com.distsnapchat.communication.receiver.MessageDecoder;

public class HeartbeatHandler implements Handler
{

	@Override
	public void handle(String msg)
	{
		Node node=MessageDecoder.extractNodeInformation(msg);
		HeartbeatBuffer.pushNode(node);
		
		
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			break;

		case Candidate:
			setLeader(node);
			break;

		case OrphanFollower:
			setLeader(node);
			break;

		case Follower:
			updateLeader(node);
			break;
		default:
			break;
		}
		
	}
	
	public void setLeader(Node node)
	{
			RAFTStatus.setVoted(false);
			RAFTStatus.setDeclaredLeader(node);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
	}
	
	public void updateLeader(Node node)
	{
		RAFTStatus.setVoted(false);
		if(!RAFTStatus.getDeclaredLeader().getNodeID().equals(node.getNodeID()))
		{
			RAFTStatus.setDeclaredLeader(node);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
		}
		
		
	}

}
