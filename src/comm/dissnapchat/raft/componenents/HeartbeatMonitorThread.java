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
			if (RAFTStatus.isLeaderElected() == true && missed==0)
			{
				heartbeatWait();
				if (HeartbeatBuffer.popNode() == null)
				{
					System.out.println("Heartbeat Missed...");
					missed++;
					RAFTStatus.afterHeartbeatMissedReset();
					
				}
				else
				{
					System.out.println("Heartbeat Received...");

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
			Thread.sleep(RAFTStatus.getHeartBeatTimeOut());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
