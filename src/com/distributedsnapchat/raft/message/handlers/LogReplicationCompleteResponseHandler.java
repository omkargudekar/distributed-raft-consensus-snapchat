package com.distributedsnapchat.raft.message.handlers;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.buffers.HeartbeatBuffer;
import com.distributedsnapchat.communication.buffers.NominationsBuffer;
import com.distributedsnapchat.communication.buffers.VoteBuffer;
import com.distributedsnapchat.communication.nodes.receiver.MessageDecoder;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.ClientMessage;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;

public class LogReplicationCompleteResponseHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		Node node=new Node(msg.getNodeId(),msg.getNodeIp(),msg.getNodePort());
		System.out.println("Inside LogReplicationCompleteResponseHandler "+node);
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
	
	
	@Override
	public void handle(ClientMessage msg)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void commitLog(Message msg)
	{
		System.out.println("Log Replication Completed on : ");
		System.out.println(msg.getNodeId()+" "+msg.getNodeIp());
	}

}
