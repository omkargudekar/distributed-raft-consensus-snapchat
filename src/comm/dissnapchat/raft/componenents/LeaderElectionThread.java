package comm.dissnapchat.raft.componenents;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("Starting leader election threads...");
		
		new Thread(new DeclareCandidacyThread()).start();
	    new Thread(new VoteCounterThread()).start();
		new Thread(new CandidacyMonitorThread()).start();
		new Thread(new HeartbeatMonitorThread()).start();
		
		
	}

}
