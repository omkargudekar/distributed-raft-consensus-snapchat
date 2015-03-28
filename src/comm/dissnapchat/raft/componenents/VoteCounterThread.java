package comm.dissnapchat.raft.componenents;

import com.distsnapchat.communication.receiver.buffers.VoteBuffer;

import comm.dissnapchat.raft.RAFTStatus;

public class VoteCounterThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("VoteCounterThread started...");
		while (true)
		{
			if (VoteBuffer.getMessageCount() ==0 || RAFTStatus.isLeaderElected() == true)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			else if ((RAFTStatus.isLeaderElected() == false) && (VoteBuffer.getMessageCount() >= (RAFTStatus.getNetwotkSize() / 2)))
			{
				declareLeader();
			
			}
		}
	}
	
	public void declareLeader()
	{
		System.out.println("****  Elected As Leader ****");
		RAFTStatus.setCuurentLeader(true);
		new Thread(new HeartbeatSenderThread()).start();
		RAFTStatus.setDeclaredLeader(RAFTStatus.getCurrentHost());
		RAFTStatus.afterLeaderElectinReset();
	}

}
