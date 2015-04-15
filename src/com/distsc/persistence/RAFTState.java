package com.distsc.persistence;

import java.io.Serializable;

public class RAFTState implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentTerm;
	public RAFTState(int currentTerm, String votedFor, String[] log) {
		super();
		this.currentTerm = currentTerm;
		this.votedFor = votedFor;
		this.log = log;
	}
	public RAFTState() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCurrentTerm() {
		return currentTerm;
	}
	public void setCurrentTerm(int currentTerm) {
		this.currentTerm = currentTerm;
	}
	public String getVotedFor() {
		return votedFor;
	}
	public void setVotedFor(String votedFor) {
		this.votedFor = votedFor;
	}
	public String[] getLog() {
		return log;
	}
	public void setLog(String[] log) {
		this.log = log;
	}
	private String votedFor;
	private String[] log;
	
}
