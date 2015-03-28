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
		
			if(RAFTStatus.isLeaderElected()==false)
			{
			
				MulticastMessage multicast=new MulticastMessage();
				multicast.send(RAFTStatus.getNodes(),"Candidate-"+RAFTStatus.getServerID()+"-localhost-8992"+"\r\n");
			
			}
			
		}
	}

}
