package comm.dissnapchat.raft.componenents;

import java.util.Timer;

import comm.dissnapchat.raft.RAFTStatus;

public class ParticipateThread implements Runnable
{
	Timer timer;

	
	@Override
	public void run()
	{
		System.out.println("Participate Thread Started");
		while(true)
		{
			
			if(RAFTStatus.isLeaderElected()==false)
			{	
				waitForTimeOut();
				new Thread(new DeclareCandidacyThread()).start();
			    new Thread(new VoteCounterThread()).start();			
				
			}
		}
		
	}
	  
	
	public void waitForTimeOut()
	{
		
		try
		{
			Thread.sleep(RAFTStatus.getRaftTimer());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}

	

}
