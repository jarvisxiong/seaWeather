package com.sea.weather.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class TimerLinster
 *
 */
@WebListener
public class TimerLinster implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public TimerLinster() {
    	System.out.println("-----启动侦听器------");
    	TimerHelp.startTask();
    	System.out.println("-----启动完成------");
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
