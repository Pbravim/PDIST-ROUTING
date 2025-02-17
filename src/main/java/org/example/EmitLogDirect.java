package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity = getSeverity(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + "Pedro Gimenes Bravim - " +  message + "'");
        }
    }

    private static String getSeverity(String[] argv) {
        return (argv.length < 1) ? "info" : argv[0]; // Default to "info" if no severity provided
    }

    private static String getMessage(String[] argv) {
        if (argv.length < 2) return "Hello, World!"; // Default message
        return String.join(" ", argv).substring(argv[0].length()).trim(); // Join message parts
    }

}