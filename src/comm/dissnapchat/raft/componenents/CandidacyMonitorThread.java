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
		Node candidate=null;
		while(RAFTStatus.isLeaderElected()==false)
		{
			
			if(CandidacyBuffer.getNodeCount()>0)
			{
					candidate=CandidacyBuffer.popNode();
					CandidacyBuffer.reset();
			
										
					new Thread(new UnicastMessage(candidate.getNodeIP(),candidate.getNodePort(),"VOTE-YES-"+RAFTStatus.getServerID())).start();;
					
					
			}
			
		}
		System.out.println("CandidacyMonitor Thread Exited");
	}
	
	

}
