package io.camunda.connector.activemq.common.model;

import java.util.Objects;

import jakarta.validation.constraints.AssertFalse;

public class ActiveMqAuthentication {

	private ActiveMqAuthenticationType authType;
	private String userName;
	private String password;
	private String brokerUrl;
	private String defaultBrokerUrl;

	@AssertFalse
	private boolean isAuthFieldsIsEmpty() {
		if (authType == ActiveMqAuthenticationType.defaults) {
			return defaultBrokerUrl == null || defaultBrokerUrl.isBlank();
		}
		if (authType == ActiveMqAuthenticationType.credentials) {
			return userName == null || userName.isBlank() || password == null || password.isBlank() || brokerUrl == null || brokerUrl.isBlank();
		}
		return true;
	}

	public ActiveMqAuthenticationType getAuthType() {
		return authType;
	}

	public void setAuthType(final ActiveMqAuthenticationType authType) {
		this.authType = authType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setUri(final String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getDefaultBrokerUrl() {
		return defaultBrokerUrl;
	}

	public void setDefaultBrokerUrl(final String defaultBrokerUrl) {
		this.defaultBrokerUrl = defaultBrokerUrl;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ActiveMqAuthentication that = (ActiveMqAuthentication) o;
		return authType == that.authType
				&& Objects.equals(userName, that.userName)
				&& Objects.equals(password, that.password)
				&& Objects.equals(brokerUrl, that.brokerUrl)
				&& Objects.equals(defaultBrokerUrl, that.defaultBrokerUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authType, userName, password, brokerUrl, defaultBrokerUrl);
	}

	@Override
	public String toString() {
		return "ActiveMqAuthentication{" + "authType=" + authType + "}";
	}
}
