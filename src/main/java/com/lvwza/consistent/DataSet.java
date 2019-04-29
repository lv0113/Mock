package com.lvwza.consistent;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class DataSet {
	
	private HashMap<String,String> dataMap = null;
	
	//解析返回报文获取的字段值集合
	private HashMap<String,String> retdataMap = new HashMap<String,String>();
	
	//配置返回报文字段的路径信息，比如xpath
	private HashMap<String,String> retdataPath = null;
	
	//根据配置的字段路径，在返回报文中获取字段值。
	public void parseReturnData(String respStr) {
		if(retdataPath == null) {
			return ;
		}
		Set keyset = retdataPath.keySet();
		Object[] keys = keyset.toArray();
		for(Object key:keys) {
			String path = retdataPath.get(key);
			//pathType 用来判断报文解析方式，比如xml，json等
			String pathType = path.split("\\|")[0];
			if("xml".equalsIgnoreCase(pathType)) {
				String value = getXmlValue(respStr, path.split("\\|")[1]);
				retdataMap.put(key.toString(), value);
			}
		}
	}
	
	private String getXmlValue(String msg,String xpath) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new ByteArrayInputStream(msg.getBytes()));
			Node node = doc.selectSingleNode(xpath);
			if(node == null) {
				return null;
			}
			return node.getStringValue();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public HashMap<String, String> getRetdataPath() {
		return retdataPath;
	}

	public void setRetdataPath(HashMap<String, String> retdataPath) {
		this.retdataPath = retdataPath;
	}

	public HashMap<String, String> getRetdataMap() {
		return retdataMap;
	}

	public void setRetdataMap(HashMap<String, String> retdataMap) {
		this.retdataMap = retdataMap;
	}

	public HashMap<String,String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String,String> dataMap) {
		this.dataMap = dataMap;
	}

}
