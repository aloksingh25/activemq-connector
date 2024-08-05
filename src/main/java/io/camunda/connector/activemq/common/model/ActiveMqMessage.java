package io.camunda.connector.activemq.common.model;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class ActiveMqMessage {

	private Object properties;
	@NotNull private Object body;
	private String deliveryMode;

	

	public Object getProperties() {
		return properties;
	}

	public void setProperties(final Object properties) {
		this.properties = properties;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(final Object body) {
		this.body = body;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveMqMessage other = (ActiveMqMessage) obj;
		return Objects.equals(body, other.body) && Objects.equals(deliveryMode, other.deliveryMode)
				&& Objects.equals(properties, other.properties);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, deliveryMode, properties);
	}

	@Override
	public String toString() {
		return "ActiveMqMessage [properties=" + properties + ", body=" + body + ", deliveryMode=" + deliveryMode + "]";
	}
}
