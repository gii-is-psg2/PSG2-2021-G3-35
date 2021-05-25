package org.springframework.samples.petclinic.configuration;


import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    /* LANGUAGUE CONFIGURATION */
    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver slr = new SessionLocaleResolver();
        //slr.setDefaultLocale(Locale.US);
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
	public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor());
    }
    
    @Bean
	public MessageSource messageSource() {
	    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setDefaultEncoding("utf-8");
	    messageSource.setBasename("messages/messages");
	    return messageSource;
	}
    
    
    /* END LANGUAGUE CONFIGURATION */

}