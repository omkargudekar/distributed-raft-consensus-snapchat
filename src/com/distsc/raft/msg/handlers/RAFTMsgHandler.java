package com.distsc.raft.msg.handlers;


import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;

public interface RAFTMsgHandler
{
	public void handle(Message msg);

}
