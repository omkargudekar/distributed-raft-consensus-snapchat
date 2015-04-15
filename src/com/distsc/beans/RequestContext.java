package com.distsc.beans;

import io.netty.channel.ChannelHandlerContext;

import com.distsc.comm.protobuf.MessageProto.Request;



public class RequestContext
{
	private ChannelHandlerContext context;
	private Request request;
	public RequestContext(ChannelHandlerContext context, Request request)
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
