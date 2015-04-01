package com.distributedsnapchat.communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distributedsnapchat.communication.protobuf.NodeMessageProto.Message;

public class UnicastMessageHandler extends SimpleChannelInboundHandler<Message> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
