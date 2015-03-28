package com.distsnapchat.communication.receiver;
import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.receiver.buffers.CandidacyBuffer;
import com.distsnapchat.communication.receiver.buffers.HeartbeatBuffer;
import com.distsnapchat.communication.receiver.buffers.ReceivedMessageBuffer;
import com.distsnapchat.communication.receiver.buffers.VoteBuffer;

import comm.dissnapchat.raft.RAFTStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class ReceiverHandler extends SimpleChannelInboundHandler<String>
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
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		if(msg.contains("Heartbeat"))
		{
			System.out.println("Heartbeat Message Received");
			Node node=MessageDecoder.decodeNode(msg);
			HeartbeatBuffer.pushNode(node);
			if(RAFTStatus.isLeaderElected()==false)
			{
				RAFTStatus.setDeclaredLeader(node);
				
			}
			else if(!RAFTStatus.getDeclaredLeader().getNodeID().equals(node.getNodeID()))
			{
				RAFTStatus.setDeclaredLeader(node);
			}
			
		}
		else if(msg.contains("Vote"))
		{
			System.out.println("Vote Message Received");
			VoteBuffer.pushMessage(msg);
		}
		else if(msg.contains("Candidate"))
		{
			System.out.println("Candidate Message Received");
			CandidacyBuffer.pushNode(MessageDecoder.decodeNode(msg));
			
		}
		else
		{
			System.out.println("Received Message  [ " + msg + " ]");
			ReceivedMessageBuffer.pushMessage(msg);
		}

	}
	
	
}
