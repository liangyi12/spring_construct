package org.litespring.util;

import java.util.ArrayList;
import java.util.List;

public class MessageTracker {
	private static List<String> MESSAGE = new ArrayList<String>();
	
	public static void addMsg(String message) {
		MESSAGE.add(message);
	}
	
	public static void clearMsg() {
		MESSAGE.clear();
	}
	
	public static List<String> getMsg() {
		return MESSAGE;
	}
}
