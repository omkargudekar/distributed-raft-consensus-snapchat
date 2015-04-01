package com.distributedsnapchat.raft.message.handlers;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.buffers.HeartbeatBuffer;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.buffers.VoteBuffer;
import com.distributedsnapchat.communication.nodes.receiver.MessageDecoder;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class HeartbeatHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		System.out.println("Inside HeartbeatHandler "+node);
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
