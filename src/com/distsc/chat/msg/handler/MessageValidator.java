package com.distsc.chat.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.app.GlobalConfiguration;
import com.distsc.chat.server.ClientContext;
import com.distsc.comm.protobuf.ClientMessage;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg.ErrorType;
import com.distsc.comm.protobuf.ClientMessage.ClientMsg.MessageType;
import com.distsc.util.SH1Generator;

public class MessageValidator
{
	
	public boolean validateRequest(ChannelHandlerContext ctx,ClientMsg msg)
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
	
	
	public boolean validateMessageSize(ChannelHandlerContext ctx,ClientMsg msg)
	{
		
		if(msg.getSerializedSize() <= GlobalConfiguration.getMessageLimit())
		{
			return true;
		}
		
		else
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.MESSAGE_SIZE)
					.setMsgText("Invalid Message Size..").build();
				ctx.writeAndFlush(message);
				return false;
		}
	}
	public boolean validateMsgText(ChannelHandlerContext ctx,ClientMsg msg)
	{
		if(msg.getChecksumMsgText().equals(SH1Generator.getStringCheckSum(msg.getChecksumMsgText())))
		{

			return true;
		}
		else
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.MESSAGE_CORRUPT)
					.setMsgText("Message Corrupted..").build();
				ctx.writeAndFlush(message);
				return false;
		}
	
		
		
	}
	public boolean validateMsgImg(ChannelHandlerContext ctx,ClientMsg msg)
	{

		if(msg.getChecksumImageBits().equals(SH1Generator.getByteChecksum(msg.getMsgImageBits().toByteArray())))
		{
			return true;
		}
		else
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.MESSAGE_CORRUPT)
					.setMsgText("Message Corrupted..").build();
				ctx.writeAndFlush(message);
				return false;
		}
	
		
		
	}
	
	public boolean checkReceiver(ChannelHandlerContext ctx,ClientMsg msg)
	{
		if(ClientContext.isExist(msg.getReceiverUserName()))
		{
			return true;
		}
		else
		{
			ClientMsg message = ClientMessage.ClientMsg.newBuilder().setMessageType(MessageType.ERROR)
					.setErrorType(ErrorType.DELIVERY_FAIL)
					.setMsgText("Delivery Failed..").build();
				ctx.writeAndFlush(message);
				return false;
		}
	
	}

}
