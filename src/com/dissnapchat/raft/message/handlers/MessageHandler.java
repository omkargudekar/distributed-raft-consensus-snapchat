package com.dissnapchat.raft.message.handlers;

import org.dissnapchat.protobuf.MessageProto.Message;

import com.distsnapchat.communication.buffers.ReceivedMessageBuffer;

public class MessageHandler implements Handler
{

	@Override
	public void handle(Message msg)
	{
		ReceivedMessageBuffer.pushMessage(msg);		
	}

}
