package com.lvwza.consistent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TcpSend implements AdapterI {
	private static Log log = LogFactory.getLog(TcpSend.class);
	private String ip = null;
	private int port = 0;
	private String encoding = "UTF-8";
	private int timeout=60000;
	private int headlen = 8;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public byte[] send(byte[] data) {
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			String msg = new String(data,"UTF-8");
			byte[] sendbyte = msg.getBytes(encoding);
			String msglen = ""+sendbyte.length;
			msglen = leftFillZero(msglen,headlen);
			log.info("send data:"+msglen+msg);
			socket = new Socket(ip,port);
			socket.setSoTimeout(timeout);
			os = socket.getOutputStream();
			os.write(msglen.getBytes());
			os.write(sendbyte);
			os.flush();
			
			is = socket.getInputStream();
			byte[] headByte = new byte[headlen];
			is.read(headByte);
			int revmsglen = Integer.parseInt(new String(headByte));
			log.info("接收长度头["+revmsglen+"]");
			
			byte[] recvByte = new byte[revmsglen];
			
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			byte[] temp = new byte[8192];
			while(true) {
				int len =is.read(temp);
				if(len == -1) {
					break;
				}
				bo.write(temp, 0, len);
			}
			recvByte = bo.toByteArray();
			
			/* 另一种读取方法
			int index = 0;
			int leftlen = revmsglen;
			while(true) {
				int len = is.read(recvByte, index, leftlen);
				if(len == leftlen || len < 0) {
					break;
				}
				index +=len;
				leftlen -=len;
			}
			*/
			String recvStr = new String(recvByte,encoding);
			log.info("recv data:"+recvStr);
			
			return recvStr.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private String leftFillZero(String msglen, int headlen) {
		StringBuilder sb = new StringBuilder();
		sb.append(msglen);
		while(sb.length() < headlen) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	public int getHeadlen() {
		return headlen;
	}

	public void setHeadlen(int headlen) {
		this.headlen = headlen;
	}

}
