package com.distsc.intercluster.msg.handlers;

import com.distsc.intercluster.msg.protobuff.ClusterMessageProto.ClusterMessage;

public interface ClusterMsgHandlerInterface
{
	
	public void handle(ClusterMessage msg);

}
