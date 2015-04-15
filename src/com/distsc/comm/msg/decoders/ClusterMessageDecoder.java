package com.distsc.comm.msg.decoders;
import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;
import com.distsc.raft.msg.handlers.HeartbeatHandler;
import com.distsc.raft.msg.handlers.LogReplicationCompleteResponseHandler;
import com.distsc.raft.msg.handlers.LogReplicationRequestHandler;
import com.distsc.raft.msg.handlers.NominationHandler;
import com.distsc.raft.msg.handlers.VoteHandler;



public class ClusterMessageDecoder
{
	
	
	public synchronized static void handle(Message msg)
	{		
		System.out.println("Message Received : "+msg.getMessageType());
		
		switch (msg.getMessageType())
		{
		
		case HEARTBEAT:
			new HeartbeatHandler().handle(msg);
			break;

		case VOTE:
			new VoteHandler().handle(msg);
			break;

		case NOMINATION:
			new NominationHandler().handle(msg);
			break;

		case LOG_REPLICATION_REQUEST:
			new LogReplicationRequestHandler().handle(msg);
			break;
		case LOG_REPLICATION_COMPLETE_NOTIFICATION:
			new LogReplicationCompleteResponseHandler().handle(msg);
			break;

		default:
			System.out.println("Unknown Message Received");
			break;
		}
		
		
	
	}
	
	
	
}
