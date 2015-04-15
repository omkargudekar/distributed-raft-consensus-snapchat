package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.UserContextMap;

public class LoginHandler implements ClientMsgHandler
{

	@Override
	public void handle(ChannelHandlerContext ctx,Request request)
	{
		
		if(UserContextMap.isExist(request.getPayload().getClientMessage().getSenderUserName()))
		{
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.INVALID_LOGIN)
					.setSenderMsgText("Username Already Taken..."))).build();
			ctx.writeAndFlush(message);
		}
		else
		{
			UserContextMap.addClientContext(request.getPayload().getClientMessage().getSenderUserName(), ctx);
			
			
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.LOGIN_SUCCESS)
					.setSenderMsgText("Logged In..."))).build();
			ctx.writeAndFlush(message);
			
			
		}		
	}
	

}
