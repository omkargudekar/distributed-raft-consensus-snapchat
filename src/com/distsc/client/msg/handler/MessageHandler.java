package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;

public class MessageHandler implements ClientMsgHandlerInterface
{

	@Override
	public void handle(ChannelHandlerContext ctx,Request msg )
	{

		
		sendMessage(ctx,msg);
		
	}
	public void sendMessage(ChannelHandlerContext ctx,Request msg)
	{

		System.out.println("Sending "+msg.getPayload().getClientMessage().getClientMessageType()
				+" From "+msg.getPayload().getClientMessage().getSenderUserName()
				+" To "+msg.getPayload().getClientMessage().getReceiverUserName());
		
		MessageValidator validator=new MessageValidator();
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{
			if(UserChannelContextMap.isExist(msg.getPayload().getClientMessage().getReceiverUserName()))
			{
				
				UserChannelContextMap.getClientContext(msg.getPayload().getClientMessage().getReceiverUserName()).writeAndFlush(msg);
				System.out.println("Message Sent.");
				Request message=MessageProto.Request.newBuilder()
						.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
						.setPayload(MessageProto.Payload.newBuilder()
						.setClientMessage(MessageProto.ClientMessage.
								newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ACKNOWLEDGE)
						.setSenderMsgText("Message Delivered To "+msg.getPayload().getClientMessage().getReceiverUserName()))).build();
					ctx.writeAndFlush(message);
			
			}
			else
			{
				Request message=MessageProto.Request.newBuilder()
						.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
						.setPayload(MessageProto.Payload.newBuilder()
						.setClientMessage(MessageProto.ClientMessage.
								newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
						.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.DELIVERY_FAIL)
						.setSenderMsgText("User Does not Exist"))).build();
					ctx.writeAndFlush(message);
			
				
			}	
		}
	}
	
	
}
