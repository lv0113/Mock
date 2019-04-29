package com.lvwza.consistent;

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;

public class TranProcess implements BeanNameAware{
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	private static Log log = LogFactory.getLog(TranProcess.class);
	//测试数据
	private DataSet data = null;
	
	//接出协议
	private AdapterI adapter = null;
	
	//交易所属模块
	private String module = null;
	
	//beanid
	private String beanName = null;
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public AdapterI getAdapter() {
		return adapter;
	}

	public void setAdapter(AdapterI adapter) {
		this.adapter = adapter;
	}

	public String execute(String tranId) throws Exception {
		//获取请求报文	
		String filePath = DispatcherServlet.class.getResource("/").getPath()+File.separator+"req"+File.separator+module+File.separator+tranId;
		log.debug("请求报文路径："+filePath);
		String reqStr = FileUtils.readFileToString(new File(filePath),"UTF-8");
		if(reqStr == null) {
			throw new Exception("未找到请求报文或请求报文配置为空");
		}
		//报文测试数据替换
		log.debug("请求报文处理前为："+reqStr);
		reqStr = dealData(reqStr,data);
		log.debug("请求报文处理后为:"+reqStr);
		
		byte[] resByte = adapter.send(reqStr.getBytes("UTF-8"));
		return new String(resByte,"UTF-8");
	}
	
	public DataSet getData() {
		return data;
	}

	public void setData(DataSet data) {
		this.data = data;
	}

	private String dealData(String reqStr,DataSet data) {
		//变量替换规则  ${CARD_NO}
		String regEx = "\\$\\{(.*?)\\}";
	    //编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(reqStr);
	    while(matcher.find()) {
	    	//${CARD_NO}
	    	String allword = matcher.group();
	    	//CARD_NO
	    	String word = matcher.group(1);
	    	
	    	//获取测试数据值,先判断是否能够在返回报文中获取。获取不到则获取配置的固定值
	    	String value = data.getRetdataMap().get(word);
	    	if(value == null) {
	    		 value = data.getDataMap().get(word);
	    		 if(value == null) {
	    			 value = "";
	    		 }
	    	} 
	    	reqStr = reqStr.replaceFirst("\\$\\{"+word+"\\}", value);
	    }
	    
	    
	    //处理随机数生成: random(5)  生成5位随机数
	    regEx = "random\\((\\d+)\\)";
	    //编译正则表达式
	    pattern = Pattern.compile(regEx);
	    matcher = pattern.matcher(reqStr);
	    while(matcher.find()) {
	    	String len = matcher.group(1);
	    	String value = random(Integer.valueOf(len));
	    	reqStr = reqStr.replaceFirst("random\\((\\d+)\\)", value);
	    }
	    
	    
	    
	    
		return reqStr;
	}
	
	private String random(int len) {
	    String base= "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    StringBuffer sb = new StringBuffer();
	    Random random = new Random();
	    for(int i =0 ; i < len ; i++) {
	    	sb.append(base.charAt(random.nextInt(base.length())));
	    }
	    return sb.toString();
	}
	
	/*
	 * 转换为16进制数
	 * @param data
	 * @return	
	 * 		16进制数
	 */
	public  String getHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; ++i) {
			String ch = Integer.toHexString(data[i] & 0xFF).toUpperCase();
			if (ch.length() == 2)
				sb.append(ch);
			else
				sb.append("0").append(ch);
		}
		return sb.toString();
	}
	
	 /**
     * 将16进制的字符串转换为byte数组
     * @param hexString
     * @return
     */
    public  byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }
    
    public byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }
}
