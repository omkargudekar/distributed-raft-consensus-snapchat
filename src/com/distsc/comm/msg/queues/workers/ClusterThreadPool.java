package com.distsc.comm.msg.queues.workers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.app.RunMain;
import com.distsc.app.config.GlobalConfiguration;
import com.distsc.raft.election.workers.DeclareCandidacyThread;
import com.distsc.raft.election.workers.HeartbeatSenderThread;
import com.distsc.raft.election.workers.LogAppendListener;
import com.distsc.raft.election.workers.LogAppendResultListener;
import com.distsc.raft.election.workers.RequestVoteResultListenerThread;
import com.distsc.server.Server;
import com.distsc.server.listener.RequestListenerThread;

public class ClusterThreadPool implements Runnable
{
	static Logger logger = LoggerFactory.getLogger(RunMain.class);
	@Override
	public void run()
	{
		logger.info("Starting Server Thread");
		new Thread(new Server()).start();
		logger.info("Starting RequestListener Thread");
		new Thread(new RequestListenerThread()).start();
		
		logger.info("Starting RequestWorker Threads");
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoRequestWorkerThreads();counter++)
				{
		    		logger.debug("Starting RequestWorker Thread"+counter);

					new Thread(new RequestWorker()).start();
				}
		    }
		}.start();
		
		logger.info("Starting NodeDiscoveryQueueWorker Threads");
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoNodeDiscoverWorkerThreads();counter++)
				{
		    		logger.debug("Starting NodeDiscoveryQueueWorker Thread"+counter);

					new Thread(new NodeDiscoveryQueueWorker()).start();
				}
		    }
		}.start();
		
		logger.info("Starting ClientMessageQueueWorker Threads");
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoClientMsgWorkerThreads();counter++)
				{
		    		logger.debug("Starting ClientMessageQueueWorker Thread"+counter);

					new Thread(new ClientMessageQueueWorker()).start();
				}
		    }
		}.start();
		
		logger.info("Starting DeclareCandidacyThread Thread");

		new Thread(new DeclareCandidacyThread()).start();

		
		logger.info("Starting RequestVoteResultListenerThread Threads");

		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoRequestVoteListenerThread();counter++)
				{
		    		logger.debug("Starting RequestVoteResultListenerThread Thread"+counter);

				    new Thread(new RequestVoteResultListenerThread()).start();
				}
		    }
		}.start();
		
		
		logger.info("Starting LogAppendListener Threads");

		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoLogAppendListener();counter++)
				{
		    		logger.debug("Starting LogAppendListener Thread"+counter);

					new Thread(new LogAppendListener()).start();
				}
		    }
		}.start();
		
		
		logger.info("Starting LogAppendResultListener Threads");

		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoLogAppendResultListener();counter++)
				{
		    		logger.debug("Starting LogAppendResultListener Thread"+counter);

					new Thread(new LogAppendResultListener()).start();
				}
		    }
		}.start();
		
		logger.info("Starting HeartbeatSenderThread Thread");
		new Thread(new HeartbeatSenderThread()).start();

	
		
		
				
	
	}

}
