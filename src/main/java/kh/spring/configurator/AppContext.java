package kh.spring.configurator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContext implements ApplicationContextAware {

	private static ApplicationContext context;
	
	// 이것이 DL
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;

	}

	public static ApplicationContext getContext() {
		return context;
	}
	

}
