package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.chat.server.ChatContext;
import com.distsc.comm.msg.protobuf.ClientMessageProto;
import com.distsc.comm.msg.protobuf.ClusterMessageProto;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg.ErrorType;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg.MessageType;
import com.distsc.comm.msg.protobuf.ClusterMessageProto.ClusterMessage;
import com.distsc.comm.msg.queues.outbound.ClusterMsgOutboundQueue;
import com.distsc.raft.RAFTStatus;

public class MessageHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,ClientMsg msg )
	{
		System.out.println(msg.getMessageType()+" From "+msg.getSenderUserName() + " For "+msg.getReceiverUserName() );
		switch (RAFTStatus.getCurrentNodeState())
		{
		case Leader:
			sendMessage(ctx,msg);
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
	public void sendMessage(ChannelHandlerContext ctx,ClientMsg msg)
	{
		System.out.println("Sending  to Message...");
		
		MessageValidator validator=new MessageValidator();
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{
			ClusterMessage clustMsg=ClusterMessageProto.ClusterMessage.newBuilder()
					.setMsgImageBits(msg.getMsgImageBits())
					.setMsgImageName(msg.getMsgImageName())
					.setMsgText(msg.getMsgText()).build();
			System.out.println("Writing  Message...");
			
			ClusterMsgOutboundQueue.pushMessage(clustMsg);
			
			ChatContext.getClientContext(msg.getReceiverUserName()).writeAndFlush(msg);
			
			
		}
		
	}
	public void redirectToLeader(ChannelHandlerContext ctx,ClientMsg msg)
	{

		System.out.println("Redirecting to leader...");
		ClientMsg message = ClientMessageProto.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.INVALID_LEADER)
				.setMsgText(RAFTStatus.getDeclaredLeader().getNodeIP()+"-"+RAFTStatus.getDeclaredLeader().getNodePort()).build();
		ctx.writeAndFlush(message);
		
		
	}
	
	public void sendError(ChannelHandlerContext ctx,ClientMsg msg)
	{
		System.out.println("Sending Error...");
		ClientMsg message = ClientMessageProto.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
				.setErrorType(ErrorType.DELIVERY_FAIL)
				.setMsgText("Please Wait...").build();
		ctx.writeAndFlush(message);
		
		
	}
	
}
