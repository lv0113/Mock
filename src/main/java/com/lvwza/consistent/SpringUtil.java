package com.lvwza.consistent;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
	public static ApplicationContext context = null;
	
	public static Object getBean(String name) {
		return context.getBean(name);
	}
}
