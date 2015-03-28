package com.distsnapchat.communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UnicastMessageHandler extends SimpleChannelInboundHandler<String> 
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

	


	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception
	{

		System.out.println("Unicast Response Ignored : " + arg1);
		
	}
}
