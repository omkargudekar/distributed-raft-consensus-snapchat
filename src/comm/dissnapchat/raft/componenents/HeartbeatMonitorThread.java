package comm.dissnapchat.raft.componenents;

import com.distsnapchat.communication.receiver.buffers.HeartbeatBuffer;

import comm.dissnapchat.raft.RAFTStatus;

public class HeartbeatMonitorThread implements Runnable
{

	@Override
	public void run()
	{
		int missed = 0;
		System.out.println("HeartBeatMoniotor Thread Started");

		while (true)
		{
			if (missed == 0 && RAFTStatus.isLeader() == false)
			{
				heartbeatWait();
				if (HeartbeatBuffer.popNode() == null)
				{
					missed++;
					RAFTStatus.afterHeartbeatMissedReset();
					
				}
				else
				{
					RAFTStatus.setUpTimer();
					HeartbeatBuffer.reset();
					
				}
			}
			

			
		}

	}
	
	public void heartbeatWait()
	{
		try
		{
			Thread.sleep(RAFTStatus.getHeartBeatFrequency());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
