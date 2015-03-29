package com.dissnapchat.raft.controlmessage.handler;

import com.distsnapchat.communication.buffers.ReceivedMessageBuffer;

public class MessageHandler implements Handler
{

	@Override
	public void handle(String msg)
	{
		ReceivedMessageBuffer.pushMessage(msg);		
	}

}
