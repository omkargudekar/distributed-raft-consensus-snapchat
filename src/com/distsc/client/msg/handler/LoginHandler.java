package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class LoginHandler implements ClientMsgHandlerInterface
{

	@Override
	public void handle(ChannelHandlerContext ctx,Request request)
	{
		
		System.out.println("Login From "+request.getPayload().getClientMessage().getSenderUserName());
		
		if(UserChannelContextMap.isExist(request.getPayload().getClientMessage().getSenderUserName()))
		{
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.INVALID_LOGIN)
					.setSenderMsgText("Username Already Taken..."))).build();
			ctx.writeAndFlush(message);
			System.out.println("Login failed "+request.getPayload().getClientMessage().getSenderUserName());
		}
		else
		{
			UserChannelContextMap.addClientContext(request.getPayload().getClientMessage().getSenderUserName(), ctx);
			
			
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.LOGIN_SUCCESS)
					.setSenderMsgText("Logged In..."))).build();
			System.out.println("Login Successful "+request.getPayload().getClientMessage().getSenderUserName());

			ctx.writeAndFlush(message);
			
			
		}		
	}
	

}
