package io.camunda.connector.activemq.outbound.model;

import java.util.Objects;

import io.camunda.connector.activemq.common.model.ActiveMqAuthentication;
import io.camunda.connector.activemq.common.model.ActiveMqAuthenticationType;
import io.camunda.connector.activemq.common.model.ActiveMqDestinationType;
import io.camunda.connector.activemq.common.model.ActiveMqMessage;
import io.camunda.connector.activemq.common.model.ActiveMqRouting;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;

public class ActiveMqRequest {

	@Valid @NotNull private ActiveMqAuthentication authentication;
	@Valid @NotNull private ActiveMqRouting routing;
	@Valid @NotNull private ActiveMqMessage message;

	@AssertFalse
	private boolean isAuthParamsNotFilling() {
		if (authentication.getAuthType() == ActiveMqAuthenticationType.defaults) {
			return authentication.getDefaultBrokerUrl() == null || authentication.getDefaultBrokerUrl().isBlank();
		}
		if (authentication.getAuthType() == ActiveMqAuthenticationType.credentials) {
			return authentication.getUserName() == null
					|| authentication.getUserName().isBlank()
					|| authentication.getPassword() == null
					|| authentication.getPassword().isBlank()
					|| authentication.getBrokerUrl() == null
					|| authentication.getBrokerUrl().isBlank();
		}
		return true;
	}
	
	@AssertFalse
	private boolean isRoutingParamsNotFilling() {
		if (routing.getDestinationType() == ActiveMqDestinationType.queue) {
			return routing.getQueuename() == null || routing.getQueuename().isBlank();
		}
		if (routing.getDestinationType() == ActiveMqDestinationType.topic) {
			return routing.getTopicname() == null || routing.getTopicname().isBlank();
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authentication, message, routing);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveMqRequest other = (ActiveMqRequest) obj;
		return Objects.equals(authentication, other.authentication) && Objects.equals(message, other.message)
				&& Objects.equals(routing, other.routing);
	}

	public ActiveMqAuthentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(ActiveMqAuthentication authentication) {
		this.authentication = authentication;
	}

	public ActiveMqRouting getRouting() {
		return routing;
	}

	public void setRouting(ActiveMqRouting routing) {
		this.routing = routing;
	}

	public ActiveMqMessage getMessage() {
		return message;
	}

	public void setMessage(ActiveMqMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ActiveMqRequest [authentication=" + authentication + ", routing=" + routing + ", message=" + message
				+ "]";
	}

	

}
