package com.sea.weather.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sea.weather.date.action.HlAction;

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
    	System.out.println("-----start linster------");
    	TimerHelp.startTask();
    	HlAction objHlAction = new HlAction();
    	objHlAction.setDate();
    	System.out.println("-----end------");
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
