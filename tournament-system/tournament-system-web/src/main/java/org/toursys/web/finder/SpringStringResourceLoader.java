package org.toursys.web.finder;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.resource.loader.IStringResourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class SpringStringResourceLoader implements IStringResourceLoader {

    private MessageSource messageSource;

    public SpringStringResourceLoader(ApplicationContext context) {
        messageSource = context.getBean(ReloadableResourceBundleMessageSource.class);
    }

    @Override
    public String loadStringResource(Class<?> clazz, String key, Locale locale, String style, String variation) {
        try {
            return messageSource.getMessage(key, new Object[0], locale);
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String loadStringResource(Component component, String key, Locale locale, String style, String variation) {
        if (component == null) {
            return null;
        }
        return loadStringResource(component.getClass(), key, locale, style, variation);
    }
}