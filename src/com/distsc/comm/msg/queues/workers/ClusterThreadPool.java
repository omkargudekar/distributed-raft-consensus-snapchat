package com.distsc.comm.msg.queues.workers;
import com.distsc.app.config.GlobalConfiguration;
import com.distsc.raft.election.workers.DeclareCandidacyThread;
import com.distsc.raft.election.workers.HeartbeatSenderThread;
import com.distsc.raft.election.workers.LogAppendListener;
import com.distsc.raft.election.workers.LogAppendResultListener;
import com.distsc.raft.election.workers.RequestVoteResultListenerThread;
import com.distsc.server.Server;
import com.distsc.server.listener.ListenerThread;

public class ClusterThreadPool implements Runnable
{

	@Override
	public void run()
	{
		new Thread(new Server()).start();
	
		new Thread(new ListenerThread()).start();
		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoRequestWorkerThreads();counter++)
				{
					new Thread(new RequestWorker()).start();
				}
		    }
		}.start();
		
		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoNodeDiscoverWorkerThreads();counter++)
				{
					new Thread(new NodeDiscoveryQueueWorker()).start();
				}
		    }
		}.start();
		
		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoClientMsgWorkerThreads();counter++)
				{
					new Thread(new ClientMessageQueueWorker()).start();
				}
		    }
		}.start();
		
		new Thread(new DeclareCandidacyThread()).start();

		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoRequestVoteListenerThread();counter++)
				{
				    new Thread(new RequestVoteResultListenerThread()).start();
				}
		    }
		}.start();
		
		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoLogAppendListener();counter++)
				{
					new Thread(new LogAppendListener()).start();
				}
		    }
		}.start();
		
		
		new Thread()
		{
		    public void run() 
		    {
		    	for(int counter=0;counter<GlobalConfiguration.getNoLogAppendResultListener();counter++)
				{
					new Thread(new LogAppendResultListener()).start();
				}
		    }
		}.start();
		new Thread(new HeartbeatSenderThread()).start();

	
		
		
				
	
	}

}
