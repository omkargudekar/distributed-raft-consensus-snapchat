package com.distsc.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.raft.RAFTStatus;

public class Persistor {
	
	static Logger logger = LoggerFactory.getLogger(Persistor.class);

	
	static String filePath;
	
	public static void setUp(String filepath)
	{	
		logger.info("Setting Up : "+filepath);
		if( Persistor.isRAFTStateFileExists() ){
			RAFTStatus.setRaftState(Persistor.readPersistedRAFTStatus());
		}else{
			RAFTStatus.createNewRAFTState();
		}
	}
	public static void persistRAFTStatus(RAFTState raftState){
		 try{
			 
			FileOutputStream raft = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(raft);   
			oos.writeObject(raftState);
			oos.close();

			logger.info("Storing persistence file to disk : ");

		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
	}

	public static RAFTState readPersistedRAFTStatus(){
		RAFTState raftState;
		 try{
			   FileInputStream raft = new FileInputStream(filePath);
			   ObjectInputStream ois = new ObjectInputStream(raft);
			   raftState = (RAFTState) ois.readObject();
			   ois.close();
			   logger.info("Reading persistence file from disk...");
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
