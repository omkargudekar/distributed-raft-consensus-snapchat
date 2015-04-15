package com.distsc.raft.msg.handlers;
import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.HeartbeatQueue;
import com.distsc.comm.msg.queues.inbound.NominationsQueue;
import com.distsc.comm.msg.queues.inbound.VotesQueue;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

public class HeartbeatHandler implements RAFTMsgHandler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		HeartbeatQueue.pushNode(node);
		
		
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			break;

		case Candidate:
			updateLeader(node);
			break;

		case Follower:
			updateLeader(node);
			break;
			
		default:
			break;
		}
		
	}

	
	public void updateLeader(Node node)
	{
		
		if(RAFTStatus.getDeclaredLeader() == null || !RAFTStatus.getDeclaredLeader().getNodeID().equals(node.getNodeID()))
		{
			System.out.println("New Elected Leader : "+node);
			RAFTStatus.setCurrentNodeState(RAFTStatus.NodeState.Follower);
			RAFTStatus.setVoted(false);
			RAFTStatus.setDeclaredLeader(node);
			NominationsQueue.reset();
			VotesQueue.reset();
			
		}
		
		
	}


}
