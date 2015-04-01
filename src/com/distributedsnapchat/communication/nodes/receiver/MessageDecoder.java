package com.distributedsnapchat.communication.nodes.receiver;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.message.handlers.HeartbeatHandler;
import com.distributedsnapchat.raft.message.handlers.LogReplicationCompleteResponseHandler;
import com.distributedsnapchat.raft.message.handlers.LogReplicationRequestHandler;
import com.distributedsnapchat.raft.message.handlers.NominationHandler;
import com.distributedsnapchat.raft.message.handlers.VoteHandler;



public class MessageDecoder
{
	
	
	public static void handle(Message msg)
	{		
		
		
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
