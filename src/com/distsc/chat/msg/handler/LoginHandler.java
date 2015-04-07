package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

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
			sendError(ctx,msg);
			break;

		case OrphanFollower:
			sendError(ctx,msg);
			break;

		case Follower:
			redirectToLeader(ctx,msg);
			break;
		default:
			break;
		}
		
	}
	public void redirectToLeader(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.INVALID_LEADER)
				.setMsgText(RAFTStatus.getDeclaredLeader().getNodeIP()+"-"+RAFTStatus.getDeclaredLeader().getNodePort()).build();
		ctx.writeAndFlush(message);
		
		
	}
	
	public void sendError(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.DELIVERY_FAIL)
				.setMsgText("Please Wait...").build();
		ctx.writeAndFlush(message);
		
		
	}
	public void connectUser(ChannelHandlerContext ctx,ClientMsg msg)
	{
		if(ClientContext.isExist(msg.getSenderUserName()))
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.INVALID_LOGIN)
					.setMsgText("Username Already Taken...").build();
			ctx.writeAndFlush(message);
		}
		else
		{
			ClientContext.addClientContext(msg.getSenderUserName(), ctx);
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.LOGIN_SUCCESS)
					.setMsgText("Logged In...").build();
			ctx.writeAndFlush(message);
		}
	}

}
