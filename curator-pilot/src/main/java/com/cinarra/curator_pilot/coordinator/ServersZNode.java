package com.cinarra.curator_pilot.coordinator;

import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.google.common.io.Closeables;

@Component
@ManagedResource(
		objectName="com.cinarra.config:name=serversZNode", 
		description="Servers ZNode info")
public class ServersZNode {
	private static Logger log = LoggerFactory.getLogger(ServersZNode.class);
	
	private PathChildrenCache serversCache;
	private CuratorFramework curator;
	private int serverCount;
	
	@ManagedAttribute
	public int getServerCount() {
		return serverCount;
	}


	@Autowired
	public ServersZNode(
			CuratorFramework curatorFramework
			) {
		serversCache = new PathChildrenCache(curatorFramework, "/servers", true);
		try {
			serversCache.start(StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			log.error("PathChildrenCache init error", e);
		}
		curator = curatorFramework;
	}
	
	
	public void printNodes() {
		CuratorFrameworkState state = curator.getState();
		
		System.out.println("\nCurrent list of Nodes As of "+new Date()+" STATE:"+state);
		List<ChildData> currentData = serversCache.getCurrentData();
		serverCount = currentData.size();
		for (ChildData childData : currentData) {
			System.out.println("  --->"+childData.getPath());
		}
		System.out.println("-------------------------------------------");
		
	}
	
	@SuppressWarnings("deprecation")
	@PreDestroy
	private void destroy() {
		Closeables.closeQuietly(serversCache);
	}
}
