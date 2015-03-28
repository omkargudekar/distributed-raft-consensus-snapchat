package com.distsnapchat.beans;

public class Node
{
	public Node()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private String nodeID;
	public Node(String nodeID, String nodeIP, int nodePort)
	{
		super();
		this.nodeID = nodeID;
		this.nodeIP = nodeIP;
		this.nodePort = nodePort;
	}
	public String getNodeID()
	{
		return nodeID;
	}
	public void setNodeID(String nodeID)
	{
		this.nodeID = nodeID;
	}
	private String nodeIP;
	private int nodePort;

	public String getNodeIP()
	{
		return nodeIP;
	}
	public Node(String nodeIP, int nodePort)
	{
		super();
		this.nodeIP = nodeIP;
		this.nodePort = nodePort;
	}
	public void setNodeIP(String nodeIP)
	{
		this.nodeIP = nodeIP;
	}
	
	public int getNodePort()
	{
		return nodePort;
	}
	public void setNodePort(int nodePort)
	{
		this.nodePort = nodePort;
	}

}
