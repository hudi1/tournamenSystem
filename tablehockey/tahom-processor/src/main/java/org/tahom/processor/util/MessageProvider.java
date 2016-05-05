package org.tahom.processor.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.tahom.processor.ApplicationContextProvider;

public class MessageProvider {

	MessageSource messageSource;

	public String getMessage(String messageCode) {
		try {
			messageSource = ApplicationContextProvider.getContextStatic().getBean(MessageSource.class);
			return messageSource.getMessage(messageCode, null, Locale.getDefault());
		} catch (Exception e) {
			return "Unresolved message code: " + messageCode;
		}
	}

}
