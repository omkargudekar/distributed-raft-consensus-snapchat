package com.distsc.intercluster.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.distsc.comm.msg.queues.ClientMsgQueue;
import com.distsc.intercluster.msg.queue.ClusterRequestContext;
import com.distsc.intercluster.proto.App.Request;


public class ClusterDiscoveryHandler extends SimpleChannelInboundHandler<Request> 
{
	static Logger logger = LoggerFactory.getLogger(ClusterClientMsgProcessor.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg)
			throws Exception 
	{
		logger.info("Processing Cluster Discovery Message");

		ClientMsgQueue.pushClusterClientRequest(new ClusterRequestContext(ctx,msg));
	}
}
