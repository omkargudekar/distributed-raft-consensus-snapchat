package com.distributedsnapchat.raft.message.handlers;

import com.distributedsnapchat.communication.protobuf.NodeMessageProto.ClientMessage;
import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;

public interface Handler
{
	public void handle(Message msg);
	public void handle(ClientMessage msg);
}
