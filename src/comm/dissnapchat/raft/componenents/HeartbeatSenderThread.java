package comm.dissnapchat.raft.componenents;

import com.distsnapchat.communication.MulticastMessage;

import comm.dissnapchat.raft.RAFTStatus;

public class HeartbeatSenderThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("heartBeatSender Thread Started");
		MulticastMessage multicast = new MulticastMessage();

		while (true)
		{
			if (RAFTStatus.isLeader() == true)
			{
				System.out.println("Sending Heartbeat..");
				multicast.send(RAFTStatus.getNodes(), "Heartbeat-" + RAFTStatus.getServerID() + "\r\n");

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
		

	}

}
