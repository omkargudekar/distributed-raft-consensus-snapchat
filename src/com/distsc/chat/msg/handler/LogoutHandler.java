package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.chat.server.ChatContext;
import com.distsc.comm.protobuf.ClientMessageProto;
import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg;
import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg.ErrorType;
import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg.MessageType;
import com.distsc.raft.RAFTStatus;

public class LogoutHandler implements ClientMsgHandler
{
	
	public void handle(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		System.out.println(msg.getMessageType()+" From "+msg.getSenderUserName());
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			disconnectUser(ctx,msg);
			break;

		case Candidate:
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
		
		ClientMsg message = ClientMessageProto.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.INVALID_LEADER)
				.setMsgText(RAFTStatus.getDeclaredLeader().getNodeIP()+"-"+RAFTStatus.getDeclaredLeader().getNodePort()).build();
		ctx.writeAndFlush(message);
		
		
	}
	
	public void sendError(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		ClientMsg message = ClientMessageProto.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.DELIVERY_FAIL)
				.setMsgText("Please Wait...").build();
		ctx.writeAndFlush(message);
		
		
	}
	public void disconnectUser(ChannelHandlerContext ctx,ClientMsg msg)
	{
		if(ChatContext.isExist(msg.getSenderUserName()))
		{	
			ctx.close();
			ChatContext.removeClientContext(msg.getSenderUserName());
		}
		
	}


}
