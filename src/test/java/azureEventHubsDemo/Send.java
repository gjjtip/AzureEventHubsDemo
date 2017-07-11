package azureEventHubsDemo;

import java.io.IOException;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.microsoft.azure.eventhubs.*;
import com.microsoft.azure.servicebus.*;

public class Send {

	static final String namespaceName = "jaygongeventhubs";
	static final String eventHubName = "eventhubdemo";
	static final String sasKeyName = "RootManageSharedAccessKey";
	static final String sasKey = "b4SUwDfWu6ZeDqjzDgwXdRiNLKN3oNL8u6M4VjG3qMs=";
	static ConnectionStringBuilder connStr = new ConnectionStringBuilder(namespaceName, eventHubName, sasKeyName,
			sasKey);

	public static void main(String[] args)
			throws ServiceBusException, ExecutionException, InterruptedException, IOException {
		byte[] payloadBytes = ("Test AMQP message from JMS"+new Date().toString()).getBytes("UTF-8");
		EventData sendEvent = new EventData(payloadBytes);

		EventHubClient ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString());
		ehClient.sendSync(sendEvent);
		System.out.println("success");
	}

}
