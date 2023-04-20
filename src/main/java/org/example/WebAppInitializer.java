package org.example;

import jakarta.servlet.*;

import jakarta.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.example.app.config.AppContextConfig;
import org.example.web.config.WebContextConfig;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
    Logger logger = Logger.getLogger(WebAppInitializer.class);

    private String TMP_FOLDER = "D:\\Java\\apache-tomcat-10.1.7\\externalUploads";
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("loading app config");
//        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//        appContext.setConfigLocation("classpath:app-config.xml");
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppContextConfig.class);
        servletContext.addListener(new ContextLoaderListener(appContext));

        logger.info("loading web config");

        //XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        //webContext.setConfigLocation("classpath:web-config.xml");

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebContextConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        ServletRegistration.Dynamic dispatcher =servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        logger.info("dispatcher ready");

        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("h2-console", new JakartaWebServlet());
        servletRegistration.setLoadOnStartup(2);
        servletRegistration.addMapping("/console/*");

        MultipartConfigElement multipartConfigElement = new  MultipartConfigElement(TMP_FOLDER, 100000, 100000 * 2, 100000 / 2);
        dispatcher.setMultipartConfig(multipartConfigElement);


    }
}
