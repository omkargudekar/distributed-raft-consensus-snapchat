package com.distsnapchat.communication.receiver;
import org.dissnapchat.protobuf.MessageProto.Message;

import com.dissnapchat.raft.RAFTStatus;
import com.dissnapchat.raft.message.handlers.HeartbeatHandler;
import com.dissnapchat.raft.message.handlers.MessageHandler;
import com.dissnapchat.raft.message.handlers.NominationHandler;
import com.dissnapchat.raft.message.handlers.VoteHandler;
import com.distsnapchat.beans.Node;



public class MessageDecoder
{
	
	
	public static void handle(Message msg)
	{
	/*	if(msg.contains("Heartbeat"))
		{
			new HeartbeatHandler().handle(msg);
			
		}
		else if(msg.contains("Vote"))
		{
			new VoteHandler().handle(msg);
		}
		else if(msg.contains("Candidate"))
		{
			new NominationHandler().handle(msg);			
		}
		else
		{
			new MessageHandler().handle(msg);
		}
		*/
		System.out.println(msg.toString());
		

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
			System.out.println("Message Received");
			new MessageHandler().handle(msg);
			break;
		}
		
		
	
	}
	
	
	
}
