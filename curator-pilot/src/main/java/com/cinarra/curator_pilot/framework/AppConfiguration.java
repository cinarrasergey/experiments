package com.cinarra.curator_pilot.framework;

import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {
	public String getZKConnectionString() {
		return "127.0.0.1:2181";
	}
}
