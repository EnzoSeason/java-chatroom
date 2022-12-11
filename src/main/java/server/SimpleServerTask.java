package server;

import common.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleServerTask implements ServerTaskRunnable {

    private ConcurrentHashMap<String, Channel> platform;
    private Socket socket;
    private ClientNameHelper clientNameHelper;
    private String clientName;

    public SimpleServerTask(ConcurrentHashMap platform, Socket socket, ClientNameHelper clientNameHelper) {
        this.platform = platform;
        this.socket = socket;
        this.clientNameHelper = clientNameHelper;
    }

    @Override
    public void run() {
        System.out.println("Connected " + socket.getRemoteSocketAddress());
        SimpleChannel channel;
        try {
            channel = new SimpleChannel(socket);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        initClient(channel);

        while (true) {
            try {
                var message = channel.receive();
                if (message.getDestination().equalsIgnoreCase(Constants.ADMIN_CLIENT)) {
                    handleServerCommand(message);
                } else {
                    handleClient(message, channel);
                }
            } catch (IOException e) {
                platform.remove(clientName).close();
                e.printStackTrace();
                return;
            }
        }
    }

    private void handleClient(Message message, Channel channel) {
        var destination = message.getDestination();
        var destinationChannel = platform.get(destination);
        if (destinationChannel == null) {
            channel.send(new SimpleMessage(Constants.ADMIN_CLIENT, destination, destination + " doesn't exist."));
        } else {
            destinationChannel.send(message);
        }
    }

    private void handleServerCommand(Message message) {
        var source = message.getSource();
        var sourceChannel = platform.get(source);
        var command = message.getContent();

        if (command.equalsIgnoreCase(Constants.SERVER_COMMAND_LOGOUT)) {
            sourceChannel.send(new SimpleMessage(Constants.ADMIN_CLIENT, source, Constants.BYE));
            sourceChannel.close();
            platform.remove(source).close();
            System.out.println(source + " left the chat room.");
        }

        if (command.equalsIgnoreCase(Constants.SERVER_COMMAND_LIST)) {
            sourceChannel.send(new SimpleMessage(Constants.ADMIN_CLIENT, source,
                    "Online: " + String.join(", ", getAllClientNames())));
        }
    }

    private void initClient(Channel channel) {
        String errorMsg = null;
        while (true) {
            channel.send(new SimpleMessage(Constants.ADMIN_CLIENT, Constants.ANON_CLIENT,
                    (errorMsg == null ? "" : "Invalid username: " + errorMsg) +
                            " Current used usernames: " + String.join(", ", getAllClientNames()) +
                            " Please enter a username different from listed usernames."));
            try {
                var message = channel.receive();
                var clientName = message.getContent();
                errorMsg = clientNameHelper.isValidName(clientName);
                if (errorMsg == null && !platform.containsKey(clientName)) {
                    this.clientName = clientName;
                    platform.put(clientName, channel);
                    channel.send(new SimpleMessage(Constants.ADMIN_CLIENT, clientName, Constants.CLIENT_NAME_CREATED));
                    channel.send(new SimpleMessage(Constants.ADMIN_CLIENT, clientName, Constants.WELCOME));
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                channel.close();
                return;
            }
        }
    }

    private ArrayList<String> getAllClientNames() {
        var adminName = Constants.CLIENT_PREFIX + Constants.ADMIN_CLIENT;
        var allClientNames = new ArrayList<String>();
        allClientNames.add(adminName);

        if (!platform.isEmpty()) {
            for (String clientName : platform.keySet()) {
                allClientNames.add(Constants.CLIENT_PREFIX + clientName);
            }
        }

        return allClientNames;
    }

}
