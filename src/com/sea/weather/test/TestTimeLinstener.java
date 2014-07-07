package com.sea.weather.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class TestTimeLinstener
 *
 */
@WebListener
public class TestTimeLinstener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public TestTimeLinstener() {
    	//System.out.println("∆Ù∂Ø≤‚ ‘");
    	//TestTimerHelp.startTask();
    	//System.out.println("Ω· ¯≤‚ ‘");
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
