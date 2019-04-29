package com.lvwza.consistent;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class CDResultParse implements ParseI {
	private static final String RET_CODE="/service/sys-header/data[@name='SYS_HEAD']/struct/data[@name='RET']/array/struct/data[@name='RET_CODE']";
	private static final String RET_MSG="/service/sys-header/data[@name='SYS_HEAD']/struct/data[@name='RET']/array/struct/data[@name='RET_MSG']";

	@Override
	public boolean isSuccess(String msg) {
		String retcode = getRetCode(msg);
		if("000000".equals(retcode)) {
			return true;
		}
		return false;
	}

	@Override
	public String getRetCode(String msg) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new ByteArrayInputStream(msg.getBytes()));
			Node node = doc.selectSingleNode(RET_CODE);
			return node.getStringValue();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public String getRetMsg(String msg) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new ByteArrayInputStream(msg.getBytes()));
			Node node = doc.selectSingleNode(RET_MSG);
			return node.getStringValue();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
