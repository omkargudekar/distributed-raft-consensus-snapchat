package com.distsc.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistor {
	
	public static void persistRAFTStatus(RAFTState raftState){
		 try{
			 
			FileOutputStream raft = new FileOutputStream("persistence/RAFTState.ser");
			ObjectOutputStream oos = new ObjectOutputStream(raft);   
			oos.writeObject(raftState);
			oos.close();
			System.out.println("Persisted RAFTStatus to disk");
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
	}

	public static RAFTState readPersistedRAFTStatus(){
		RAFTState raftState;
		 try{
			   FileInputStream raft = new FileInputStream("persistence/RAFTState.ser");
			   ObjectInputStream ois = new ObjectInputStream(raft);
			   raftState = (RAFTState) ois.readObject();
			   ois.close();
			   System.out.println("Reading RAFTState from disk");
			   return raftState;
	 	   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
		
	}
	
	public static boolean isRAFTStateFileExists(){
		File file = new File("persistence/RAFTState.ser");
		if( file.exists() ){
			return true;
		}
		return false;
	}
	
}
