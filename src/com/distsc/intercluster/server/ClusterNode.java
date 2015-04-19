package com.distsc.intercluster.server;


public class ClusterNode
{
	@Override
	public String toString()
	{
		return "Node [ClusterID=" + clusterID + ", nodeIP=" + nodeIP + ", nodePort=" + nodePort + "]";
	}
	public ClusterNode()
	{
		super();
	}
	private String clusterID;
	public ClusterNode(String clusterID, String nodeIP, int nodePort)
	{
		super();
		this.clusterID = clusterID;
		this.nodeIP = nodeIP;
		this.nodePort = nodePort;
		
	}
	public String getClusterID()
	{
		return clusterID;
	}
	public void setNodeID(String clusterID)
	{
		this.clusterID = clusterID;
	}
	private String nodeIP;
	private int nodePort;
	public String getNodeIP()
	{
		return nodeIP;
	}
	public ClusterNode(String nodeIP, int nodePort)
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
