package com.github.corneil.springdata.couchbase.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.WriteResultChecking;
import org.springframework.data.couchbase.core.query.Consistency;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.support.IndexManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


@Configuration
@EnableCouchbaseRepositories
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
	private final static Logger logger = LoggerFactory.getLogger(CouchbaseConfig.class);

	@Value("${couchbase.host}")
	protected String host;

	@Value("${couchbase.password}")
	protected String password;

	@Value("${couchbase.port}")
	protected String port;

	@Override
	protected List<String> getBootstrapHosts() {
		logger.info("couchbase.host:" + host);
		StringTokenizer tokens = new StringTokenizer(host, ", ");
		List<String> hosts = new ArrayList<String>();
		while (tokens.hasMoreTokens()) {
			hosts.add(tokens.nextToken());
		}
		return hosts;
	}

	@Override
	public CouchbaseTemplate couchbaseTemplate() throws Exception {
		CouchbaseTemplate template = super.couchbaseTemplate();
		template.setWriteResultChecking(WriteResultChecking.EXCEPTION);
		template.setDefaultConsistency(Consistency.READ_YOUR_OWN_WRITES);
		return template;
	}

	@Override
	protected String getBucketName() {
		return "sd-demo";
	}

	@Override
	protected String getBucketPassword() {
		return password;
	}

	@Override
	public IndexManager indexManager() {
		return new IndexManager(true, true, true);
	}
}
