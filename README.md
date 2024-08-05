# ActiveMQ Connector

ActiveMQ connector lets you setup an ActiveMQ producer task and allows to send messages to ActiveMQ queue or topic from Camunda process.

# Prerequisite
* [ActiveMQ](https://activemq.apache.org/components/classic/download/) - Apache ActiveMQ
* Camunda Desktop Modeler/ Web Modeler
* Camunda - 8 environment setup (Zeebe Engine)

# Working with ActiveMQ Connector
* Using [activeMQ-connector.json](https://github.com/aloksingh25/activemq-connector/blob/main/element-templates/activemq-connector.json), ActiveMQ connector can be made available to the modeler/ web modeler for use.

<img width="432" alt="Screenshot 2024-02-01 175454" src="https://github.com/user-attachments/assets/3855ccf2-875a-4307-8437-91f58b23073c">

* Provide the broker URL, authentication credentials of ActiveMQ along with the destination type, queue/ topic name, message as request.
* Execute the connector code.
* Message can be verified in the ActiveMQ queue/ topic.
