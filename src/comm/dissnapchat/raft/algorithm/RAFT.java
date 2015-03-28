package comm.dissnapchat.raft.algorithm;

import comm.dissnapchat.raft.componenents.LeaderElectionThread;


public class RAFT implements Runnable
{

	@Override
	public void run()
	{
		new Thread(new LeaderElectionThread()).start();

	}

}


