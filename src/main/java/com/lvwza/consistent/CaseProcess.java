package com.lvwza.consistent;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CaseProcess {
	private static Log log = LogFactory.getLog(CaseProcess.class);
	
	private List<TranProcess> trans = null;
	
	private ParseI parse = null;
	

	public ParseI getParse() {
		return parse;
	}

	public void setParse(ParseI parse) {
		this.parse = parse;
	}

	public List<TranProcess> getTrans() {
		return trans;
	}

	public void setTrans(List<TranProcess> trans) {
		this.trans = trans;
	}
	
	public String execute(String caseid) {
		log.info("开始执行场景。。。。"+caseid);
		StringBuffer sb = new StringBuffer();
		for(TranProcess tran : trans) {
			sb.append(tran.getBeanName()).append(":");
			try {
				String respStr = tran.execute(tran.getBeanName());
				//缓存需要的返回字段。
				tran.getData().parseReturnData(respStr);
				
				System.out.println("响应报文字段："+tran.getData().getRetdataMap());
				
				if(parse != null) {
					boolean issuccess = parse.isSuccess(respStr);
					String retCode = parse.getRetCode(respStr);
					String retMsg = parse.getRetMsg(respStr);
					sb.append("ISSUCCESS=").append(issuccess).append(",");
					sb.append("RET_CODE=").append(retCode).append(",");
					sb.append("RET_MSG=").append(retMsg).append("<br>");
				} else {
					sb.append(respStr).append("<br>").append("===================================================================").append("<br>");
				}
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("ISSUCCESS=").append("false").append(",");
				sb.append("RET_CODE=").append("ERR999").append(",");
				sb.append("RET_MSG=").append(e.getMessage()).append("<br>");
			}
		}
		return sb.toString();
	}
	
	
}
