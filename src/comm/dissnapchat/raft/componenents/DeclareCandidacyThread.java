package comm.dissnapchat.raft.componenents;

import com.distsnapchat.communication.MulticastMessage;

import comm.dissnapchat.raft.RAFTStatus;

public class DeclareCandidacyThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("DeclareCandidacy Thread Started");
		while(true)
		{
		
			if(RAFTStatus.isLeaderElected()==false && RAFTStatus.isNomionated()==false)
			{
				RAFTStatus.setNomionated(true);
				MulticastMessage multicast=new MulticastMessage();
				multicast.send(RAFTStatus.getNodes(),"Candidate-"+RAFTStatus.getServerID()+"-192.168.0.1-8992"+"\r\n");
				
			
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
