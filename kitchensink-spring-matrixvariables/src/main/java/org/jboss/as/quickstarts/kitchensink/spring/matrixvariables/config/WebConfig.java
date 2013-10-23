package org.jboss.as.quickstarts.kitchensink.spring.matrixvariables.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setRemoveSemicolonContent(false);
        return handlerMapping;
    } 
    
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerApapter = super.requestMappingHandlerAdapter();
        
        /* 
         * The RequestMappingHandlerAdapter provides a flag called "ignoreDefaultModelOnRedirect" that can be used to indicate 
         * the content of the default Model should never be used if a controller method redirects. Instead the controller 
         * method should declare an attribute of type RedirectAttributes or if it doesn't do so no attributes should be 
         * passed on to RedirectView. Both the MVC namespace and the MVC Java config keep this flag set to false in order 
         * to maintain backwards compatibility. However, for new applications we recommend setting it to true.
         * 
         * The use of the redirect in the create Member method will pull in the model from the Filter if run after the 
         * Filter.  This keeps that from happening. 
         */
        handlerApapter.setIgnoreDefaultModelOnRedirect(true);
        return handlerApapter;
    }
}