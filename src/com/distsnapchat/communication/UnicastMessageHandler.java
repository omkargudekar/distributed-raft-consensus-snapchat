package com.distsnapchat.communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.dissnapchat.protobuf.MessageProto.Message;

public class UnicastMessageHandler extends SimpleChannelInboundHandler<Message> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Message arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
