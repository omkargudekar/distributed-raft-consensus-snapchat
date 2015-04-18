package com.distsc.app.config;

import java.util.ArrayList;

import com.distsc.beans.Node;

public class GlobalConfiguration
{	

	private static int messageLimit=0;

	public static int getNoRequestVoteResultListenerThreads()
	{
		return noRequestVoteResultListenerThreads;
	}

	public static void setNoRequestVoteResultListenerThreads(int noRequestVoteResultListenerThreads)
	{
		GlobalConfiguration.noRequestVoteResultListenerThreads = noRequestVoteResultListenerThreads;
	}

	public static int getNoRequestVoteListenerThread()
	{
		return noRequestVoteListenerThread;
	}

	public static void setNoRequestVoteListenerThread(int noRequestVoteListenerThread)
	{
		GlobalConfiguration.noRequestVoteListenerThread = noRequestVoteListenerThread;
	}

	public static int getNoLogAppendListener()
	{
		return noLogAppendListener;
	}

	public static void setNoLogAppendListener(int noLogAppendListener)
	{
		GlobalConfiguration.noLogAppendListener = noLogAppendListener;
	}

	public static int getNoLogAppendResultListener()
	{
		return noLogAppendResultListener;
	}

	public static void setNoLogAppendResultListener(int noLogAppendResultListener)
	{
		GlobalConfiguration.noLogAppendResultListener = noLogAppendResultListener;
	}


	private static Node currentNode=null;
	private static ArrayList<Node> nodes=new ArrayList<Node>();
	private static String clientMessageStorageDir="client_messages/";
	private static int noRequestWorkerThreads=1;
	private static int noNodeDiscoverWorkerThreads=1;
	private static int noClientMsgWorkerThreads=1;
	private static int noRequestVoteResultListenerThreads=1;
	private static int noRequestVoteListenerThread=1;
	private static int noLogAppendListener=1;
	private static int noLogAppendResultListener=1;

	

	

	public static int getNoClientMsgWorkerThreads()
	{
		return noClientMsgWorkerThreads;
	}

	public static void setNoClientMsgWorkerThreads(int noClientMsgWorkerThreads)
	{
		GlobalConfiguration.noClientMsgWorkerThreads = noClientMsgWorkerThreads;
	}

	public static int getNoNodeDiscoverWorkerThreads()
	{
		return noNodeDiscoverWorkerThreads;
	}

	public static void setNoNodeDiscoverWorkerThreads(int noNodeDiscoverWorkerThreads)
	{
		GlobalConfiguration.noNodeDiscoverWorkerThreads = noNodeDiscoverWorkerThreads;
	}

	public static int getNoRequestWorkerThreads()
	{
		return noRequestWorkerThreads;
	}

	public static void setNoRequestWorkerThreads(int noRequestWorkerThreads)
	{
		GlobalConfiguration.noRequestWorkerThreads = noRequestWorkerThreads;
	}

	public static String getClientMessageStorageDir()
	{
		return clientMessageStorageDir;
	}

	public static int getMessageLimit()
	{
		return messageLimit;
	}

	public static void setMessageLimit(int messageLimit)
	{
		GlobalConfiguration.messageLimit = messageLimit;
	}
	public static void setClientMessageStorageDir(String clientMessageStorageDir)
	{
		GlobalConfiguration.clientMessageStorageDir = clientMessageStorageDir;
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
	

	

}
