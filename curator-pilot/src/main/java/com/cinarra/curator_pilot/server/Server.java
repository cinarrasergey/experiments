package com.cinarra.curator_pilot.server;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Server {
	//this is log
	private static Logger log = LoggerFactory.getLogger(Server.class);
	
	@Autowired
	public Server(CuratorFramework curator) throws Exception {
		curator.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/servers/new");
	}
	
	
	public static void main(String[] args) {
		//another test modification
    	ApplicationContext springContainer = new AnnotationConfigApplicationContext(ServerSpringConfig.class);
    	Server server = springContainer.getBean(Server.class);
    	log.info("Server started");
    	while(true){}
	}
}
