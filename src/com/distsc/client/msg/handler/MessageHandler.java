package com.distsc.client.msg.handler;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;
import com.distsc.network.maps.UserChannelContextMap;
import com.distsc.server.ClusterMulticast;

public class MessageHandler implements ClientMsgHandlerInterface
{

	@Override
	public void handle(ChannelHandlerContext ctx,Request msg )
	{

		
		sendMessage(ctx,msg);
		
	}
	public void sendMessage(ChannelHandlerContext ctx,Request msg)
	{
		MessageValidator validator=new MessageValidator();
		
		if(validator.validateMessageSize(ctx,msg)==true)
		{
			if(UserChannelContextMap.isExist(msg.getPayload().getClientMessage().getReceiverUserName()))
			{
				UserChannelContextMap.getClientContext(msg.getPayload().getClientMessage().getReceiverUserName()).writeAndFlush(msg);
			}
			else
			{
				forwardMessage(ctx,msg);
			}	
		}
	}
	public void forwardMessage(ChannelHandlerContext ctx,Request msg)
	{
		ClusterMulticast multicast=new ClusterMulticast();
		multicast.send(msg);
	}
	
}
