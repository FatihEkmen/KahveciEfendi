package com.fatih.dbconnection;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Connection
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class Connection {

    public static void initConnection(HttpServletRequest vaadinRequest, Object target) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(target);
        ServletContext servletContext = vaadinRequest.getSession().getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

}
