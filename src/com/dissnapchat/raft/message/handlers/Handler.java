package com.dissnapchat.raft.message.handlers;

import org.dissnapchat.protobuf.MessageProto.Message;

public interface Handler
{
	public void handle(Message msg);
}
