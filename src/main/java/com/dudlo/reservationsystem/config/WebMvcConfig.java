package com.dudlo.reservationsystem.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@PropertySource("classpath:exception.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	   @Autowired
	   public Environment environment;
	   
	
	// 
	// DefaultServletHandlerConfigurer is intended to be used when the Spring
	// MVC DispatcherServlet is mapped
	// to "/" thus overriding the Servlet container's default handling of static
	// resources.
/*	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}*/
	/*Stores registrations of resource handlers for serving static resources such as images, 
	 * css files and others through Spring MVC including setting cache headers optimized 
	 * for efficient loading in a web browser. Resources can be served out of locations 
	 * under web application root, from the classpath, and others.
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry) {
       handlerRegistry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver .setViewClass(JstlView.class);
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setRedirectContextRelative(true);
		return internalResourceViewResolver;}
	  }
