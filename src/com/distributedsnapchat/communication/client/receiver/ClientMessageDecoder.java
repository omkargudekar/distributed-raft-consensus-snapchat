package com.distributedsnapchat.communication.client.receiver;
import com.distributedsnapchat.beans.Node;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;
import com.distributedsnapchat.raft.RAFTStatus;
import com.distributedsnapchat.raft.message.handlers.HeartbeatHandler;
import com.distributedsnapchat.raft.message.handlers.MessageHandler;
import com.distributedsnapchat.raft.message.handlers.NominationHandler;
import com.distributedsnapchat.raft.message.handlers.VoteHandler;



public class ClientMessageDecoder
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
		//System.out.println(msg.toString());
		

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
