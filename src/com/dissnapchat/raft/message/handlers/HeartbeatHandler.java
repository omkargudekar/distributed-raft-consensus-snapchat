package com.dissnapchat.raft.message.handlers;
import org.dissnapchat.protobuf.MessageProto.Message;

import com.dissnapchat.raft.RAFTStatus;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.buffers.HeartbeatBuffer;
import com.distsnapchat.communication.buffers.NominationsBuffer;
import com.distsnapchat.communication.buffers.VoteBuffer;
import com.distsnapchat.communication.receiver.MessageDecoder;

public class HeartbeatHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
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
			System.out.println("New Elected Leader : "+node);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
			RAFTStatus.setVoted(false);
			RAFTStatus.setDeclaredLeader(node);
			NominationsBuffer.reset();
			VoteBuffer.reset();
			
	}
	
	public void updateLeader(Node node)
	{
		
		if(RAFTStatus.getDeclaredLeader() == null || !RAFTStatus.getDeclaredLeader().getNodeID().equals(node.getNodeID()))
		{
			System.out.println("New Elected Leader : "+node);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
			RAFTStatus.setVoted(false);
			RAFTStatus.setDeclaredLeader(node);
			NominationsBuffer.reset();
			VoteBuffer.reset();
			
		}
		
		
	}

}
