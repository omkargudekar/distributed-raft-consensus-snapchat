package comm.dissnapchat.raft.componenents;

import com.distsnapchat.communication.receiver.buffers.VoteBuffer;

import comm.dissnapchat.raft.RAFTStatus;

public class VoteCounterThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("VoteCounter Thread Started");
		while(VoteBuffer.getMessageCount()<(RAFTStatus.getNetwotkSize()/2) || RAFTStatus.isLeaderElected()==false)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if((RAFTStatus.isLeaderElected()==false) && (VoteBuffer.getMessageCount()>(RAFTStatus.getNetwotkSize()/2)))
		{
			RAFTStatus.setLeader(true);
			RAFTStatus.afterHeartbeatMissedReset();
			new Thread(new HeartbeatSenderThread()).start();
		}
		System.out.println("VoteCounter Thread Exited");
	}

}
