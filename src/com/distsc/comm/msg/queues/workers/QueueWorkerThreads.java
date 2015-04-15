package com.distsc.comm.msg.queues.workers;

public class QueueWorkerThreads implements Runnable
{

	@Override
	public void run()
	{

		new Thread(new ClientMessageMsgQueueWorker()).start();
		new Thread(new NodeDiscoveryMsgQueueWorker()).start();
		new Thread(new RequestWorker()).start();
	}

}
