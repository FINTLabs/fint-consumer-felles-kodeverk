package no.fint.consumer.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.hazelcast.config.*;
import io.micrometer.core.instrument.binder.JvmGcMetrics;
import io.micrometer.core.instrument.binder.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.ProcessorMetrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import no.fint.cache.CacheManager;
import no.fint.cache.FintCacheManager;
import no.fint.cache.HazelcastCacheManager;

@Configuration
public class Config {

	@Value("${fint.consumer.cache-manager:default}")
	private String cacheManagerType;

	@Bean
	public CacheManager<?> cacheManager() {
		switch (cacheManagerType.toUpperCase()) {
		case "HAZELCAST":
			return new HazelcastCacheManager<>();
		default:
			return new FintCacheManager<>();
		}
	}

	@Value("${fint.hazelcast.members}")
	private String members;

	@Bean
	public com.hazelcast.config.Config hazelcastConfig() {
		com.hazelcast.config.Config cfg = new ClasspathXmlConfig("fint-hazelcast.xml");
		return cfg.setNetworkConfig(new NetworkConfig().setJoin(new JoinConfig().setTcpIpConfig(new TcpIpConfig().setMembers(Arrays.asList(members.split(","))).setEnabled(true)).setMulticastConfig(new MulticastConfig().setEnabled(false))));
	}

	@Value("${server.context-path:}")
	private String contextPath;
		
	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		objectMapper.setDateFormat(new ISO8601DateFormat());
	}

	@Qualifier("linkMapper")
	@Bean
	public Map<String, String> linkMapper() {
		return new HashMap<>();
	}

	String fullPath(String path) {
		return String.format("%s%s", contextPath, path);
	}

	@Bean
	JvmThreadMetrics threadMetrics() {
		return new JvmThreadMetrics();
	}

	@Bean
	ProcessorMetrics processorMetrics() {
		return new ProcessorMetrics();
	}

	@Bean
	JvmGcMetrics gcMetrics() {
		return new JvmGcMetrics();
	}	

}
