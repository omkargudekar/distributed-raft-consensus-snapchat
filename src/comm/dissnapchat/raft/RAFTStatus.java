package comm.dissnapchat.raft;

import java.util.ArrayList;
import java.util.Random;

import com.distsnapchat.beans.Node;
import com.distsnapchat.communication.receiver.buffers.CandidacyBuffer;
import com.distsnapchat.communication.receiver.buffers.VoteBuffer;

public class RAFTStatus
{
	private static Node currentHost=null;
	private static ArrayList<Node> nodes=new ArrayList<Node>();
	private static Node declaredLeader=null;
	private static boolean nominated=false;
	private static int raftTimer=360;
	private static int heartBeatTimeOut=5000;
	private static int heartBeatFrequency=1000;
	private static int netwotkSize=2;
	private static boolean cuurentLeader=false;
	
	public static void  setUpCurrentHost(String machineID,String IP,int port)
	{
		currentHost=new Node();
		currentHost.setNodeID(machineID);
		currentHost.setNodeIP(IP);
		currentHost.setNodePort(port);
	}
	
	

	public static Node getCurrentHost()
	{
		return currentHost;
	}

	public static void setCurrentHost(Node currentHost)
	{
		RAFTStatus.currentHost = currentHost;
	}

	public static boolean isNominated()
	{
		return nominated;
	}

	public static void setNominated(boolean nominated)
	{
		RAFTStatus.nominated = nominated;
	}
	
	public static void afterLeaderElectinReset()
	{
		setNominated(false);
		VoteBuffer.reset();
		CandidacyBuffer.reset();
		
	}
	
	public static void afterHeartbeatMissedReset()
	{
		setNominated(false);
		declaredLeader=null;

	}
	
	
	
	

	public static Node getDeclaredLeader()
	{
		return declaredLeader;
	}

	public static void setDeclaredLeader(Node declaredLeader)
	{
		RAFTStatus.declaredLeader = declaredLeader;
	}
	

	public static int getHeartBeatFrequency()
	{
		return heartBeatFrequency;
	}


	public static void setHeartBeatFrequency(int heartBeatFrequency)
	{
		RAFTStatus.heartBeatFrequency = heartBeatFrequency;
	}






	
	public static boolean isCuurentLeader()
	{
		return cuurentLeader;
	}



	public static void setCuurentLeader(boolean cuurentLeader)
	{
		RAFTStatus.cuurentLeader = cuurentLeader;
	}



	public static ArrayList<Node> getNodes()
	{
		return nodes;
	}


	public static void setNodes(ArrayList<Node> nodes)
	{
		RAFTStatus.nodes = nodes;
	}
	
	public static boolean isLeaderElected()
	{
		if(getDeclaredLeader()==null)
		{
			return false;
		}
		return true;
	}

	public static int getRaftTimer()
	{
		return raftTimer;
	}


	public static void setRaftTimer(int raftTimer)
	{
		RAFTStatus.raftTimer = raftTimer;
	}


	public static int getHeartBeatTimeOut()
	{
		return heartBeatTimeOut;
	}


	public static void setHeartBeatTimeOut(int heartBeatTimeOut)
	{
		RAFTStatus.heartBeatTimeOut = heartBeatTimeOut;
	}


	public static int getNetwotkSize()
	{
		return netwotkSize;
	}


	public static void setNetwotkSize(int netwotkSize)
	{
		RAFTStatus.netwotkSize = netwotkSize;
	}
	
	
	public static void setUpTimer() 
	{

	    Random rand = new Random();

	    int randomNum = rand.nextInt((3000 - 1500) + 1) + 1500;
	    
	    setRaftTimer(randomNum);
	}
	
}
