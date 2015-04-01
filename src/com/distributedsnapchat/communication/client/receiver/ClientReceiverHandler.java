package com.distributedsnapchat.communication.client.receiver;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.distributedsnapchat.communication.protobuf.ClientMessageProto.ClientMessage;


public class ClientReceiverHandler extends SimpleChannelInboundHandler<ClientMessage>
{

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
	protected void channelRead0(ChannelHandlerContext arg0, ClientMessage msg)
			throws Exception 
	{
			System.out.println("[RAW] Client Message Received ");
			ClientMessageDecoder.handle(msg);
	}
	
	
}
