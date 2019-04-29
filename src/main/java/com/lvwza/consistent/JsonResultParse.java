package com.lvwza.consistent;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class JsonResultParse implements ParseI {
	
	private String retCodeName = null;
	private String retMsgName= null;
	private String retCodeSuccess = null;

	public String getRetCodeSuccess() {
		return retCodeSuccess;
	}

	public void setRetCodeSuccess(String retCodeSuccess) {
		this.retCodeSuccess = retCodeSuccess;
	}

	public String getRetCodeName() {
		return retCodeName;
	}

	public void setRetCodeName(String retCodeName) {
		this.retCodeName = retCodeName;
	}

	public String getRetMsgName() {
		return retMsgName;
	}

	public void setRetMsgName(String retMsgName) {
		this.retMsgName = retMsgName;
	}

	@Override
	public boolean isSuccess(String msg) {
		if(retCodeSuccess == null) {
			return true;
		}
		
		String retCode = getRetCode(msg);
		if(retCodeSuccess.equalsIgnoreCase(retCode)) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getRetCode(String msg) {
		JSONObject object = JSONObject.parseObject(msg);
		String retCode = (String)JSONPath.eval(object,retCodeName);
		return retCode;
	}

	@Override
	public String getRetMsg(String msg) {
		if(retMsgName == null) {
			return "";
		}
		JSONObject object = JSONObject.parseObject(msg);
		String retMsg = (String)JSONPath.eval(object,retMsgName);
		return retMsg;
	}
	
	public static void main(String args[]) {
		String str = " {\"version\":\"1.0\",\"txnType\":\"SA020\", \"sendInsCode\":\"1\", \"queryId\":\"random(20)\", \"encryptData\":\"\",\"cerVer\":\"1\" ,\"signature\":\"1\",\"cerFile\":\"1\",\"priAccNo\":\"${CARD_NO}\", \"subAccNo\":\"${SUB_ACCT_NO}\",\"txnAmt\":\"1000\",\"reqResvFld\":\"\"}";
		JSONObject object = JSONObject.parseObject(str);
		System.out.println(JSONPath.eval(object, "$queryId"));
	}

}
