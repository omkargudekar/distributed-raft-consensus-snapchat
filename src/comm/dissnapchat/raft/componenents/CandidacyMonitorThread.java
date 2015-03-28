package comm.dissnapchat.raft.componenents;


import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.UnicastMessage;
import com.distsnapchat.communication.receiver.buffers.CandidacyBuffer;

import comm.dissnapchat.raft.RAFTStatus;

public class CandidacyMonitorThread implements Runnable
{

	@Override
	public void run()
	{
		System.out.println("CandidacyMonitor Thread Started");
		while(true)
		{
			if(RAFTStatus.isLeaderElected()==false && CandidacyBuffer.getNodeCount()>0)
			{
				vote();
			
			}
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	
	}
	
	public void vote()
	{

		Node candidate=CandidacyBuffer.popNode();	
		System.out.println("Voting for candidate : "+candidate);
		CandidacyBuffer.reset();
		new Thread(new UnicastMessage(candidate.getNodeIP(),candidate.getNodePort(),"Vote-"+RAFTStatus.getCurrentHost().getNodeID()+"-"+RAFTStatus.getCurrentHost().getNodeIP()+"-"+RAFTStatus.getCurrentHost().getNodePort()+"\r\n")).start();;
				

		
	}
	
	

}
