package com.distsc.comm.msg.handlers;


import com.distsc.comm.protobuf.NodeMessageProto.Message;

public interface Handler
{
	public void handle(Message msg);

}
