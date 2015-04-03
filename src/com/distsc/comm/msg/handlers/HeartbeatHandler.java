package com.distsc.comm.msg.handlers;
import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.HeartbeatBuffer;
import com.distsc.comm.msg.queues.inbound.NominationsBuffer;
import com.distsc.comm.msg.queues.inbound.VoteBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

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
