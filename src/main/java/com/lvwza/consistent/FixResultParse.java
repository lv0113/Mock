package com.lvwza.consistent;

public class FixResultParse implements ParseI {
	//响应码起始位置（不包括长度头）
	private int retCodeIndex=0;
	//响应码长度
	private int retCodeLen=6;
	//响应信息起始位置（不包括长度头）
	private int retMsgIndex=0;
	//响应信息长度
	private int retMsglen=30;
	//成功响应码
	private String retCodeSuccess="000000";
	public int getRetCodeIndex() {
		return retCodeIndex;
	}

	public void setRetCodeIndex(int retCodeIndex) {
		this.retCodeIndex = retCodeIndex;
	}

	public int getRetCodeLen() {
		return retCodeLen;
	}

	public void setRetCodeLen(int retCodeLen) {
		this.retCodeLen = retCodeLen;
	}

	public int getRetMsgIndex() {
		return retMsgIndex;
	}

	public void setRetMsgIndex(int retMsgIndex) {
		this.retMsgIndex = retMsgIndex;
	}

	public int getRetMsglen() {
		return retMsglen;
	}

	public void setRetMsglen(int retMsglen) {
		this.retMsglen = retMsglen;
	}

	

	public String getRetCodeSuccess() {
		return retCodeSuccess;
	}

	public void setRetCodeSuccess(String retCodeSuccess) {
		this.retCodeSuccess = retCodeSuccess;
	}

	@Override
	public boolean isSuccess(String msg) {
		String retcode = getRetCode(msg);
		if(retCodeSuccess.equals(retcode)) {
			return true;
		}
		return false;
	}

	@Override
	public String getRetCode(String msg) {
		return msg.substring(retCodeIndex, retCodeIndex+retCodeLen);
	}

	@Override
	public String getRetMsg(String msg) {
		if(retMsglen == 0) {
			return "";
		}
		return msg.substring(retMsgIndex, retMsgIndex+retMsglen);
	}

}
