package com.distsc.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.distsc.raft.RAFTStatus;

public class Persistor {
	
	public static void persistRAFTStatus(RAFTStatus raftStatus){
		 try{
			 
			FileOutputStream raft = new FileOutputStream("persistence/RAFTstatus.ser");
			ObjectOutputStream oos = new ObjectOutputStream(raft);   
			oos.writeObject(raftStatus);
			oos.close();
			System.out.println("Persisted RAFTStatus to disk");
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
	}

	public static RAFTStatus readPersistedRAFTStatus(){
		RAFTStatus raftStatus;
		 try{
			 
			   FileInputStream raft = new FileInputStream("persistence/RAFTstatus.ser");
			   ObjectInputStream ois = new ObjectInputStream(raft);
			   raftStatus = (RAFTStatus) ois.readObject();
			   ois.close();
			   System.out.println("Reading RAFTStatus from disk");
			   return raftStatus;
	 	   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
		
	}
	
}
