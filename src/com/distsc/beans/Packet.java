package com.distsc.beans;

import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class Packet
{
	private Node node;
	private Message msg;
	public Node getNode()
	{
		return node;
	}
	public void setNode(Node node)
	{
		this.node = node;
	}
	public Message getMsg()
	{
		return msg;
	}
	public void setMsg(Message msg)
	{
		this.msg = msg;
	}

}
