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
			while (RAFTStatus.isLeader() == true)
			{
				multicast.send(RAFTStatus.getNodes(), "Hearbeat from " + RAFTStatus.getServerID() + "\r\n");

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
