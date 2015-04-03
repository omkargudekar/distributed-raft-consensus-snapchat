package com.distsc.comm.msg.decoders;
import com.distsc.comm.msg.handlers.HeartbeatHandler;
import com.distsc.comm.msg.handlers.LogReplicationCompleteResponseHandler;
import com.distsc.comm.msg.handlers.LogReplicationRequestHandler;
import com.distsc.comm.msg.handlers.NominationHandler;
import com.distsc.comm.msg.handlers.VoteHandler;
import com.distsc.comm.protobuf.NodeMessageProto.Message;



public class ServerMessageDecoder
{
	
	
	public static void handle(Message msg)
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
