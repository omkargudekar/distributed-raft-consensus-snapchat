package com.distsc.intercluster.msg.queue;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.intercluster.proto.App.Request;





public class ClusterRequestContext
{
	private ChannelHandlerContext context;
	private Request request;
	public ClusterRequestContext(ChannelHandlerContext context, Request request)
	{
		super();
		this.context = context;
		this.request = request;
	}
	public ChannelHandlerContext getContext()
	{
		return context;
	}
	public void setContext(ChannelHandlerContext context)
	{
		this.context = context;
	}
	public Request getRequest()
	{
		return request;
	}
	public void setRequest(Request request)
	{
		this.request = request;
	}
}
