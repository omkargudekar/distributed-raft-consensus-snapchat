package com.distsc.network.maps.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.distsc.comm.msg.queues.NodeDiscoveryQueue;
import com.distsc.comm.protobuf.MessageProto.Request;

public class NodeDiscoveryHandler extends SimpleChannelInboundHandler<Request> 
{
	static Logger logger = LoggerFactory.getLogger(NodeDiscoveryHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{
		
		logger.info(msg.toString());
		NodeDiscoveryQueue.push(msg);
	}
}
