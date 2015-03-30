package com.dissnapchat.raft.message.handlers;

import com.distsnapchat.communication.buffers.ReceivedMessageBuffer;

public class MessageHandler implements Handler
{

	@Override
	public void handle(String msg)
	{
		ReceivedMessageBuffer.pushMessage(msg);		
	}

}
