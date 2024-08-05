package io.camunda.connector.activemq.supplier;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.activemq.ActiveMQConnectionFactory;

import io.camunda.connector.activemq.common.model.ActiveMqAuthentication;
import io.camunda.connector.activemq.common.model.ActiveMqAuthenticationType;
import io.camunda.connector.activemq.common.model.ActiveMqRouting;
import jakarta.jms.ConnectionFactory;

public class ConnectionFactorySupplier {

	private static final String ADMIN = "admin";

	public ConnectionFactory createFactory(
			final ActiveMqAuthentication authentication, final ActiveMqRouting routing)
					throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
		final var factory = new ActiveMQConnectionFactory();
		if (authentication.getAuthType() == ActiveMqAuthenticationType.defaults) {
			factory.setBrokerURL(authentication.getDefaultBrokerUrl());
			factory.setUserName(ADMIN);
			factory.setPassword(ADMIN);
		} else {
			factory.setUserName(authentication.getUserName());
			factory.setPassword(authentication.getPassword());
			factory.setBrokerURL(authentication.getBrokerUrl());
		}
		return factory;
	}
}
