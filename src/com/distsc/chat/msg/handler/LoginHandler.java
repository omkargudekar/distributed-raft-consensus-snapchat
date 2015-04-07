package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.app.GlobalConfiguration;
import com.distsc.chat.server.ClientContext;
import com.distsc.comm.protobuf.ClientMessage;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg.ErrorType;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg.MessageType;
import com.distsc.raft.RAFTStatus;

public class LoginHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			connectUser(ctx,msg);
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
	
	public void connectUser(ChannelHandlerContext ctx,ClientMsg msg)
	{
		if(ClientContext.isExist(msg.getSenderUserName()))
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.INVALID_LOGIN)
					.setMsgText("Username Exists").build();
			ctx.writeAndFlush(message);
		}
		else
		{
			
		}
	}

}
