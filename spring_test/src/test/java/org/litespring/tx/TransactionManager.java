package org.litespring.tx;

import org.litespring.util.MessageTracker;

public class TransactionManager {
	
	public void start() {
		System.out.println("start");
		MessageTracker.addMsg("start");
	}
	
	public void commit(){
		System.out.println("commit");
		MessageTracker.addMsg("commit");
	}
	
	public void roolback() {
		System.out.println("roolback");
		MessageTracker.addMsg("roolback");
	}
}
