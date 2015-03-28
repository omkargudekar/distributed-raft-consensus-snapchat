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
		
			if(RAFTStatus.isLeaderElected()==false && RAFTStatus.isNominated()==false)
			{
				RAFTStatus.setNominated(true);
				MulticastMessage multicast=new MulticastMessage();
				multicast.send(RAFTStatus.getNodes(),"Candidate-"+RAFTStatus.getCurrentHost().getNodeID()+"-"+RAFTStatus.getCurrentHost().getNodeIP()+"-"+RAFTStatus.getCurrentHost().getNodePort()+"\r\n");
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
