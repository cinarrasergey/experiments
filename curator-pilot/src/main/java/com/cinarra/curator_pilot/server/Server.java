package com.cinarra.curator_pilot.server;

import javax.annotation.PreDestroy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Server {
	//this is : log
	private static Logger log = LoggerFactory.getLogger(Server.class);
	
	@Autowired
	public Server(CuratorFramework curator) throws Exception {
		curator.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/servers/new");
	}
	
	@PreDestroy
	public void destroy() {
		log.warn("Destroying Server....");
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		//another test modification
    	AbstractApplicationContext springContainer = new AnnotationConfigApplicationContext(ServerSpringConfig.class);
    	try{
        	Server server = springContainer.getBean(Server.class);
        	int timeToLiveInSec=20;
        	log.info("Server started");
        	for (int i = timeToLiveInSec ; i >0 ; i--) {
            	log.info("Time to live: "+i+" seconds.");
            	Thread.sleep(1000);
    		}
        	log.info("Server finished");
    	}
    	finally{
        	log.info("Closing Spring Context");
    		springContainer.close();
    	}
 	}
}
