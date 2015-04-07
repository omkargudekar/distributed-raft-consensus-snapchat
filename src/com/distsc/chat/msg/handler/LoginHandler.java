package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.ClientMessage.ClientMsg;
import com.distsc.raft.RAFTStatus;

public class LoginHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			break;

		case Candidate:
			break;

		case OrphanFollower:
			break;

		case Follower:
			break;
		default:
			break;
		}
		
	}

}
