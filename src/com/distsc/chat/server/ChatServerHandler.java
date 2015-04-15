package com.distsc.chat.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.distsc.chat.msg.handler.MessageValidator;
import com.distsc.comm.msg.decoders.ClientMessageDecoder;
import com.distsc.comm.protobuf.ClientMessageProto.ClientMsg;


public class ChatServerHandler extends SimpleChannelInboundHandler<ClientMsg>
{

	MessageValidator validator =new MessageValidator();
	
	
	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		channels.add(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ClientMsg msg) throws Exception 
	{	
				
				ClientMessageDecoder.handle(ctx,msg);
	}
	
	
}
