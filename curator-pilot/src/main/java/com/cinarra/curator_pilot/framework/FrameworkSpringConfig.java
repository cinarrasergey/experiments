package com.cinarra.curator_pilot.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;

@Configuration
@ComponentScan(basePackages={
		"com.cinarra.curator_pilot.framework"
})
public class FrameworkSpringConfig {
	private Logger log = LoggerFactory.getLogger(FrameworkSpringConfig.class);

	
	//================== ZOOKEEPER
	@Bean
	public CuratorFramework curatorFramework(AppConfiguration config){
		log.info("creating zookeeper client...");
		CuratorFramework client = CuratorFrameworkFactory.newClient(
				config.getZKConnectionString(), 
				new RetryNTimes(3,2000)
			);
		log.info("starting zookeeper client...");
		client.start();
		return client ;
	}
	
	//==================== Spring JMX support
	@Bean
	public MBeanExporter mBeanExporter(){
		AnnotationJmxAttributeSource attributeSource = new AnnotationJmxAttributeSource();
		MetadataMBeanInfoAssembler assembler = new MetadataMBeanInfoAssembler(attributeSource);
		MetadataNamingStrategy namingStrategy = new MetadataNamingStrategy(attributeSource);
		MBeanExporter mBeanExporter = new MBeanExporter();
		mBeanExporter.setAssembler(assembler);
		mBeanExporter.setNamingStrategy(namingStrategy);
		mBeanExporter.setAutodetect(true);
		return mBeanExporter;
	}
}
