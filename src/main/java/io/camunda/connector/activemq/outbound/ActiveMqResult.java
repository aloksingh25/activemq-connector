package io.camunda.connector.activemq.outbound;

public class ActiveMqResult {
	private String statusResult;

	public ActiveMqResult(final String statusResult) {
		this.statusResult = statusResult;
	}

	public static ActiveMqResult success() {
		return new ActiveMqResult("success");
	}

	public String getStatusResult() {
		return statusResult;
	}

	public void setStatusResult(final String statusResult) {
		this.statusResult = statusResult;
	}

	@Override
	public String toString() {
		return "ActiveMqResult{" + "statusResult='" + statusResult + "'" + "}";
	}
}
