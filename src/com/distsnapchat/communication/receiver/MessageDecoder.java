package com.distsnapchat.communication.receiver;

import com.distsnapchat.beans.Node;


public class MessageDecoder
{
	 public static Node decodeNode(String msg)
	 {
		 	String[] msg_parts = msg.split("-");
		 	Node node=new Node();
		 	node.setNodeID(msg_parts[1]);
		 	node.setNodeIP(msg_parts[2]);
		 	node.setNodePort(Integer.parseInt(msg_parts[3]));
		 	
		 	return node;
		 
		 
	 }
}
