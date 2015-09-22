package com.dudlo.reservationsystem.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.dudlo.reservationsystem.ApplicationConfig;

/**
 * 
 */
public class ContextInitializer implements WebApplicationInitializer {// Configuring
																		// ServletContext
																		// programic
	// private static final String CONFIG_LOCATION =
	// "com.dudlo.reservationsystem";
	private static final String MAPPING_URL = "/";

	private static final Logger log = LoggerFactory
			.getLogger(ContextInitializer.class);

	// ServletContext is created by the web container at time of deploying the
	// project. This object can be used to get configuration information from

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.setConfigLocation(ApplicationConfig.class.getPackage().getName());
		return ctx;
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		// The object of ServletContext provides an interface between the
		// container and servlet.
		// Log4j configuration listener registration
		servletContext
				.addListener(org.springframework.web.util.Log4jConfigListener.class);

		log.info("######## Init Application start #######");

		WebApplicationContext context = this.getContext();
		// This interface
		// adds a
		// getServletContext()
		// method to the
		// generic
		// ApplicationContext
		// interface
		// WebApplicationContext implementations need to detect
		// ServletContextAware beans and invoke the setServletContext method
		// accordingly.

		servletContext.addListener(new ContextLoaderListener(context));

		servletContext.addListener(new ServletContextListener() {
			@Override
			public void contextInitialized(
					ServletContextEvent servletContextEvent) {
				// try {
				// Manifests.DEFAULT.append(new
				// ServletMfs(servletContextEvent.getServletContext()));
				// } catch (IOException ex) {
				// log.error("", ex);
				// }
			}

			@Override
			public void contextDestroyed(ServletContextEvent servletContextEvent) {
			}
		});
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(MAPPING_URL);
		log.info("######## Init Application was correctly started #######");

	}

}
