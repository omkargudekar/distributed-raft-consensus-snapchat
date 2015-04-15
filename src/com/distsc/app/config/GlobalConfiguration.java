package com.distsc.app.config;

import java.util.ArrayList;

import com.distsc.beans.Node;

public class GlobalConfiguration
{
	
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
}
