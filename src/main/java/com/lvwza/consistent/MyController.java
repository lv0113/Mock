package com.lvwza.consistent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	Log log = LogFactory.getLog(MyController.class);

	@RequestMapping("/tran")
    public RetBean tran(@RequestParam(value="id") String tranId) {
		String respStr = null;
		//单交易处理
		try {
			TranProcess tp = (TranProcess)SpringUtil.getBean(tranId);
			respStr = tp.execute(tranId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常：",e);
			respStr = "fail";
		}
		
        return new RetBean(respStr);
    }
	
	
	@RequestMapping("/case")
    public RetBean trancase(@RequestParam(value="caseid") String caseId) {
		String respStr = null;
		//场景处理
		try { 
			CaseProcess cs = (CaseProcess)SpringUtil.getBean(caseId);
			respStr = cs.execute(caseId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异常：",e);
			respStr = "fail";
		}
        return new RetBean(respStr);
    }
}
