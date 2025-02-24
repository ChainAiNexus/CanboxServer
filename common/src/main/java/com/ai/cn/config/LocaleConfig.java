package com.ai.cn.config;


import com.ai.cn.service.I18nService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 *
 */
@Configuration
public class LocaleConfig {

    /**
     *         locale
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new DataVizLocalResolver();
    }


    @Bean
    public I18nService i18nService() {
        return new I18nService(messageSource());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        Locale.setDefault(Locale.CHINESE);
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");// name of the resource bundle
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
