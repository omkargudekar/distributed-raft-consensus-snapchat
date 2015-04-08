package com.distsc.comm.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.comm.msg.protobuf.NodeMessageProto.Message;

public class OutboundConnectionHandler extends SimpleChannelInboundHandler<Message> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception 
	{
	
			
	}
}
