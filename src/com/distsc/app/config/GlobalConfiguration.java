package com.distsc.app.config;

import java.util.ArrayList;

import com.distsc.beans.Node;
import com.distsc.comm.msg.queues.AppendEntriesQueue;
import com.distsc.comm.msg.queues.AppendEntriesResultQueue;
import com.distsc.comm.msg.queues.RequestVoteMsgQueue;
import com.distsc.comm.msg.queues.RequestVoteResultMsgQueue;
import com.distsc.raft.RAFTStatus;

public class GlobalConfiguration
{
	private static int totalVotes=0;
	public static int getTotalVotes()
	{
		return totalVotes;
	}

	public static void setTotalVotes(int totalVotes)
	{
		GlobalConfiguration.totalVotes = totalVotes;
	}

	private static int currentTerm;
	public static int getCurrentTerm()
	{
		return currentTerm;
	}

	public static void setCurrentTerm(int currentTerm)
	{
		GlobalConfiguration.currentTerm = currentTerm;
	}

	private static int messageLimit=900000;
	public static int getMessageLimit()
	{
		return messageLimit;
	}

	public static void setMessageLimit(int messageLimit)
	{
		GlobalConfiguration.messageLimit = messageLimit;
	}

	private static String clientMessageStorageDir="client_messages/";
	public static String getClientMessageStorageDir()
	{
		return clientMessageStorageDir;
	}

	public static void setClientMessageStorageDir(String clientMessageStorageDir)
	{
		GlobalConfiguration.clientMessageStorageDir = clientMessageStorageDir;
	}

	private static int clinetListenerPort=4000;

	private static Node currentNode=null;
	
	private static ArrayList<Node> nodes=new ArrayList<Node>();
	public static int getClinetListenerPort()
	{
		return clinetListenerPort;
	}

	public static void setClinetListenerPort(int clinetListenerPort)
	{
		GlobalConfiguration.clinetListenerPort = clinetListenerPort;
	}
	
	public static synchronized int getNetwotkSize()
	{
		return nodes.size();
	}

	public static Node getCurrentNode()
	{
		return currentNode;
	}

	public static void setCurrentNode(Node currentNode)
	{
		GlobalConfiguration.currentNode = currentNode;
	}

	public static ArrayList<Node> getNodes()
	{
		return nodes;
	}

	public static void setNodes(ArrayList<Node> nodes)
	{
		GlobalConfiguration.nodes = nodes;
	}
	
	public static void reset()
	{
		RAFTStatus.setVoted(false);
		AppendEntriesQueue.reset();
		AppendEntriesResultQueue.reset();
		RequestVoteMsgQueue.reset();
		RequestVoteResultMsgQueue.reset();
	}
}
