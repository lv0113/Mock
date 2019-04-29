package com.lvwza.consistent;

public interface ParseI {
	public boolean isSuccess(String msg);
	
	public String getRetCode(String msg);
	
	public String getRetMsg(String msg);
}
