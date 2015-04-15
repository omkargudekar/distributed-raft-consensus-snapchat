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
				
			}
			else
			{
				
				forwardMessage(ctx,msg);
				System.out.println("User not connected to node. Message forwarded to other clusters");
			}	
		}
	}
	public void forwardMessage(ChannelHandlerContext ctx,Request msg)
	{
		ClusterMulticast multicast=new ClusterMulticast();
		multicast.send(msg);
	}
	
}
