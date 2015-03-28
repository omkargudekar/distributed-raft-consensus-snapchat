package comm.dissnapchat.raft.componenents;

public class LeaderElectionThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("LeaderElection Thread Started");
		new Thread(new ParticipateThread()).start();
		new Thread(new CandidacyMonitorThread()).start();
		new Thread(new HeartbeatMonitorThread()).start();
		
		
	}

}
