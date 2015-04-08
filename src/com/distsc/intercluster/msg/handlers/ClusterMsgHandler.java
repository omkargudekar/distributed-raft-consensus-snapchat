package com.distsc.intercluster.msg.handlers;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.chat.server.ClientContext;
import com.distsc.comm.msg.protobuf.ClientMessageProto;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg;
import com.distsc.comm.msg.protobuf.ClientMessageProto.ClientMsg.MessageType;
import com.distsc.intercluster.msg.protobuff.ClusterMessageProto.ClusterMessage;

public class ClusterMsgHandler implements ClusterMsgHandlerInterface
{

	@Override
	public void handle(ClusterMessage msg)
	{
	
		System.out.println("********** Cluster Message Received....");
	
	}

	
	public void sendMessage(ClusterMessage msg)
	{
		ClientMsg message = ClientMessageProto.ClientMsg.newBuilder().setMessageType(MessageType.MESSAGE)
				.setMsgText(msg.getMsgText())
				.setMsgImageBits(msg.getMsgImageBits())
				.setSenderUserName(msg.getSenderUserName())
				.setReceiverUserName(msg.getReceiverUserName())
				.setMsgImageName(msg.getMsgImageName()).build();
				
				try
				{
					ChannelHandlerContext ctx=ClientContext.getClientContext(message.getReceiverUserName()) ;
					
					if(ctx!=null)
					{
						ctx.writeAndFlush(message);
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
	}
}
