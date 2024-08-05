/*
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.activemq.common.model;

import java.util.Objects;

import jakarta.validation.constraints.AssertFalse;

public class ActiveMqRouting {

	protected ActiveMqDestinationType destinationType;
	protected String queuename;
	protected String topicname;
	
	@AssertFalse
	private boolean isRoutingFieldEmpty() {
		if (destinationType == ActiveMqDestinationType.queue) {
			return queuename == null || queuename.isBlank();
		}
		if (destinationType == ActiveMqDestinationType.topic) {
			return topicname == null || topicname.isBlank();
		}
		return true;
	}
	
	public ActiveMqDestinationType getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(ActiveMqDestinationType destinationType) {
		this.destinationType = destinationType;
	}

	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}

	public String getTopicname() {
		return topicname;
	}

	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(destinationType, queuename, topicname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveMqRouting other = (ActiveMqRouting) obj;
		return destinationType == other.destinationType && Objects.equals(queuename, other.queuename)
				&& Objects.equals(topicname, other.topicname);
	}

	@Override
	public String toString() {
		return "ActiveMqRouting [destinationType=" + destinationType + ", queuename=" + queuename + ", topicname="
				+ topicname + "]";
	}
	
}
