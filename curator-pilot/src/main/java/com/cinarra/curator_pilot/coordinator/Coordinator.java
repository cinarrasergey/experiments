package com.cinarra.curator_pilot.coordinator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Coordinator {
	private static Logger log = LoggerFactory.getLogger(Coordinator.class);

	@Autowired
	private ServersZNode servers;
	
	
	private void listen() throws Exception{
		log.info("=============== WELCOME1 ================" );
		
		while(true) {
			servers.printNodes();
			Thread.sleep(2000);
		}
		
	}

	
	public static void main(String[] args) throws Exception {
    	ApplicationContext springContainer = new AnnotationConfigApplicationContext(CoordinatorSpringConfig.class);
    	Coordinator coordinator = springContainer.getBean(Coordinator.class);
    	coordinator.listen();
  
	}

}
