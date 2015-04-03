package com.distsc.comm.msg.handlers;
import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.inbound.HeartbeatBuffer;
import com.distsc.comm.msg.queues.inbound.NominationsBuffer;
import com.distsc.comm.msg.queues.inbound.VoteBuffer;
import com.distsc.comm.protobuf.NodeMessageProto.Message;
import com.distsc.raft.RAFTStatus;

public class LogReplicationCompleteResponseHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		HeartbeatBuffer.pushNode(node);
		
		
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			commitLog(msg);
			break;

		case Candidate:
			break;

		case OrphanFollower:
			break;

		case Follower:
			break;
		default:
			break;
		}
		
	}
	

	public void commitLog(Message msg)
	{
		System.out.println("Log Replication Completed on : ");
		System.out.println(msg.getNodeId()+" "+msg.getNodeIp());
	}

}
