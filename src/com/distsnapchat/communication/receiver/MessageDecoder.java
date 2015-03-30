package com.distsnapchat.communication.receiver;
import com.dissnapchat.raft.message.handlers.HeartbeatHandler;
import com.dissnapchat.raft.message.handlers.MessageHandler;
import com.dissnapchat.raft.message.handlers.NominationHandler;
import com.dissnapchat.raft.message.handlers.VoteHandler;
import com.distsnapchat.beans.Node;



public class MessageDecoder
{
	
	
	public static void handle(String msg)
	{
		if(msg.contains("Heartbeat"))
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
	}
	
	
	
	 public static Node extractNodeInformation(String msg)
	 {
		 	String[] msg_parts = msg.split("-");
		 	Node node=new Node();
		 	node.setNodeID(msg_parts[1]);
		 	node.setNodeIP(msg_parts[2]);
		 	node.setNodePort(Integer.parseInt(msg_parts[3]));
		 	
		 	return node;
	 }
}
