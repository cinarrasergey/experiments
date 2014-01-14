package com.cinarra.curator_pilot.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={
		"com.cinarra.curator_pilot.framework",
		"com.cinarra.curator_pilot.server"		
})
public class ServerSpringConfig {

}
