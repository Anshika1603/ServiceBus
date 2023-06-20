package com.knoldus.servicebus;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class ServicebusApplication {
	static final String connectionString = "https://queuedemo.servicebus.windows.net/";

	// The topic name
	static final String topicName = "queue";

	// Number of messages to be sent to the topic
	static final int numOfMessages = 3;

	public static void main(String[] args) {


		ServiceBusSenderClient sender = new ServiceBusClientBuilder()
				.connectionString(connectionString)
				.sender()
				.queueName(topicName)
				.buildClient();

		AtomicInteger sequenceNumber = new AtomicInteger(1);

		for (int i = 0; i < numOfMessages; i++) {
			ServiceBusMessage message = new ServiceBusMessage("Message " + sequenceNumber.getAndIncrement());
			message.setPartitionKey("my-partition-key");
			sender.sendMessage(message);
		}

		System.out.printf("A batch of %d messages has been published to the topic.%n", numOfMessages);

		// Close the sender
		sender.close();

		System.out.println("Press any key to end the application");
		try {
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}



