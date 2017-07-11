package azureEventHubsDemo;

import com.microsoft.azure.eventprocessorhost.*;
import com.microsoft.azure.servicebus.ConnectionStringBuilder;

import java.util.concurrent.ExecutionException;

import com.microsoft.azure.eventhubs.EventData;

public class EventProcessorSample
{
    public static void main(String args[])
    {
        final String consumerGroupName = "$Default";
    	final String namespaceName = "jaygongeventhubs";
        final String eventHubName = "eventhubdemo";
        final String sasKeyName = "RootManageSharedAccessKey";
        final String sasKey = "b4SUwDfWu6ZeDqjzDgwXdRiNLKN3oNL8u6M4VjG3qMs=";

        final String storageAccountName = "jaygongeventhubstorage";
        final String storageAccountKey = "vXH30Q26Sxe199bfjqAp41GoBzg/brRQUwEacnp3NmR5R3PsBn36g95f2DGxppupE4gOrS4PbSgPUL694f174Q==";
        final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=" + storageAccountName + ";AccountKey=" + storageAccountKey;

        ConnectionStringBuilder eventHubConnectionString = new ConnectionStringBuilder(namespaceName, eventHubName, sasKeyName, sasKey);

        EventProcessorHost host = new EventProcessorHost(eventHubName, consumerGroupName, eventHubConnectionString.toString(), storageConnectionString);

        System.out.println("Registering host named " + host.getHostName());
        EventProcessorOptions options = new EventProcessorOptions();
        options.setExceptionNotification(new ErrorNotificationHandler());
        try
        {
            host.registerEventProcessor(EventProcessor.class, options).get();
        }
        catch (Exception e)
        {
            System.out.print("Failure while registering: ");
            if (e instanceof ExecutionException)
            {
                Throwable inner = e.getCause();
                System.out.println(inner.toString());
            }
            else
            {
                System.out.println(e.toString());
            }
        }

        System.out.println("Press enter to stop");
        try
        {
            System.in.read();
            host.unregisterEventProcessor();

            System.out.println("Calling forceExecutorShutdown");
            EventProcessorHost.forceExecutorShutdown(120);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        System.out.println("End of sample");
    }
}