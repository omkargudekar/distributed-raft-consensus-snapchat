package com.distsc.raft.msg.handlers;
import com.distsc.beans.Node;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;
import com.distsc.comm.msg.queues.inbound.HeartbeatQueue;
import com.distsc.raft.RAFTStatus;

public class LogReplicationCompleteResponseHandler implements RAFTMsgHandler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		HeartbeatQueue.pushNode(node);
		
		
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			commitLog(msg);
			break;

		case Candidate:
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
