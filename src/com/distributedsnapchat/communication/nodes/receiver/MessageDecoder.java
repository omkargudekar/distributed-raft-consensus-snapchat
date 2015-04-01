package com.distributedsnapchat.communication.nodes.receiver;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.message.handlers.HeartbeatHandler;
import com.distributedsnapchat.raft.message.handlers.NominationHandler;
import com.distributedsnapchat.raft.message.handlers.VoteHandler;



public class MessageDecoder
{
	
	
	public static void handle(Message msg)
	{		

		switch (msg.getMessageType())
		{
		case HEARTBEAT:
			System.out.println("Heartbeat Received");
			new HeartbeatHandler().handle(msg);
			break;

		case VOTE:
			System.out.println("Vote Received");
			new VoteHandler().handle(msg);
			break;

		case NOMINATION:
			System.out.println("Nomination Received");
			new NominationHandler().handle(msg);
			break;

		default:
			System.out.println("Unknown Message Received");
			break;
		}
		
		
	
	}
	
	
	
}
