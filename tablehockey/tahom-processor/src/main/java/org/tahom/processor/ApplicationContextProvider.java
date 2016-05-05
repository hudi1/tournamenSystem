package org.tahom.processor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	public ApplicationContext getContext() {
		return getContextStatic();
	}

	public static ApplicationContext getContextStatic() {
		if (context != null) {
			return context;
		} else {
			throw new IllegalStateException("Aplikacni kontext neni dostupny");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public void unsafeSetApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public ApplicationContext unsafeGetApplicationContext() {
		return context;
	}
}
