package com.distsc.raft.msg.handlers;


import com.distsc.comm.protobuf.NodeMessageProto.Message;

public interface RAFTMsgHandler
{
	public void handle(Message msg);

}
