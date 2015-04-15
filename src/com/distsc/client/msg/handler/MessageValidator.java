package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;
import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.protobuf.MessageProto;
import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.UserContextMap;
import com.distsc.util.SH1Generator;
public class MessageValidator
{
	
	public boolean validateRequest(ChannelHandlerContext ctx,Request msg)
	{
		boolean valid=true;
		
		if(valid==true)
		{
			valid=checkReceiver(ctx,msg);
		}
		if(valid==true)
		{
			valid=validateMessageSize(ctx,msg);
		}
		if(valid==true)
		{
			valid=validateMsgText(ctx,msg);
		}
		
		if(valid==true)
		{
			valid=validateMsgImg(ctx,msg);
		}
		
		return valid;
	}
	
	
	public boolean validateMessageSize(ChannelHandlerContext ctx,Request msg)
	{
		
		if(msg.getSerializedSize() <= GlobalConfiguration.getMessageLimit())
		{
			return true;
		}
		
		else
		{
			
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.MESSAGE_SIZE)
					.setSenderMsgText("Invalid Message Size..."))).build();
			
			
				ctx.writeAndFlush(message);
				return false;
		}
	}
	public boolean validateMsgText(ChannelHandlerContext ctx,Request request)
	{
		
		if(request.getPayload().getClientMessage().getSenderMsgChecksumMsgText().equals(SH1Generator.getStringCheckSum(request.getPayload().getClientMessage().getSenderMsgText())))
		{

			return true;
		}
		else
		{
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.MESSAGE_CORRUPT)
					.setSenderMsgText("Message Text Corrupt..."))).build();
			
			
				ctx.writeAndFlush(message);
				return false;
		}
	
		
		
	}
	public boolean validateMsgImg(ChannelHandlerContext ctx,Request msg)
	{

		if(msg.getPayload().getClientMessage().getSenderMsgChecksumImageBytes().equals(SH1Generator.getByteChecksum(msg.getPayload().getClientMessage().getSenderMsgImageBytes().toByteArray())))
		{
			return true;
		}
		else
		{
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.MESSAGE_CORRUPT)
					.setSenderMsgText("Image Corrupt..."))).build();
			
				ctx.writeAndFlush(message);
				return false;
		}
	
		
		
	}
	
	public boolean checkReceiver(ChannelHandlerContext ctx,Request msg)
	{
		if(UserContextMap.isExist(msg.getPayload().getClientMessage().getReceiverUserName()))
		{
			return true;
		}
		else
		{
			Request message=MessageProto.Request.newBuilder()
					.setMessageHeader(Request.MessageHeader.ClientMessageMsg)
					.setPayload(MessageProto.Payload.newBuilder()
					.setClientMessage(MessageProto.ClientMessage.
							newBuilder().setClientMessageType(MessageProto.ClientMessage.ClientMessageType.ERROR)
					.setClientMessageErrorType(MessageProto.ClientMessage.ClientMessageErrorType.DELIVERY_FAIL)
					.setSenderMsgText("Client Offline..."))).build();
			
				ctx.writeAndFlush(message);
				return false;
		}
	
	}

}
