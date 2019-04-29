package com.lvwza.consistent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class DispatcherServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(DispatcherServlet.class);
	
	private WebApplicationContext wac = null;

	public DispatcherServlet() {
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		wac = ContextLoader.getCurrentWebApplicationContext();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("开始进入调度处理servlet。。。。");
		String tranId = req.getParameter("tranid");
		String caseId = req.getParameter("caseid");
		String respStr = "";
		
		if(tranId != null && !"".equals(tranId)) {
			//单交易处理
			try {
				TranProcess tp = (TranProcess)wac.getBean(tranId);
				respStr = tp.execute(tranId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("异常：",e);
				respStr = "fail";
			}
		} else if(caseId != null && !"".equals(caseId)) {
			//场景处理
			try { 
				CaseProcess cs = (CaseProcess)wac.getBean(caseId);
				respStr = cs.execute(caseId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("异常：",e);
				respStr = "fail";
			}
			
		} else {
			respStr="参数错误！";
		}
		
		
		resp.getOutputStream().write(respStr.getBytes());
		resp.getOutputStream().close();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	

}
