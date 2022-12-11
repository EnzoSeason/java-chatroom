package client;

import common.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient implements Client {
    private String name;
    private String messageDestination;
    private SimpleChannel channel;
    private Scanner scanner = new Scanner(System.in);

    public SimpleClient(String server) throws IOException {
        System.out.println("Starting Simple Chat Client...");
        var socket = new Socket(server, Constants.SERVER_PORT);
        System.out.println("Connected with the Simple Chat Server");
        channel = new SimpleChannel(socket);
    }

    @Override
    public void start() throws IOException {
        initName();

        var readThread = new Thread(() -> {
            while (true) {
                try {
                    var message = channel.receive();
                    if (message.getSource().equalsIgnoreCase(Constants.ADMIN_CLIENT)
                            && message.getContent().equalsIgnoreCase(Constants.BYE)) {
                        channel.close();
                        System.out.println("Left the Chat, Bye");
                        System.exit(0);
                    }
                    System.out.println("from \"" + message.getSource() + "\":" + message.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                    channel.close();
                    System.exit(-2);
                }
            }
        });
        readThread.start();

        var writeThread = new Thread(() -> {
           while (true) {
               try {
                   channel.send(generateMessage());
               } catch (Exception e) {
                   e.printStackTrace();
                   System.exit(-3);
               }
           }
        });
        writeThread.start();
    }

    private Message generateMessage() {
        while (true) {
            var line = scanner.nextLine();
            String content;
            String destination;
            try {
                if (line.startsWith(Constants.CLIENT_PREFIX)) {
                    destination = line.substring(1, line.indexOf(Constants.SPACE_STR));
                    var clientNameHelper = new SimpleClientNameHelper();
                    var namingError = clientNameHelper.isValidMessageDestination(destination);
                    if (namingError == null) {
                        messageDestination = destination;
                        content = line.substring(line.indexOf(Constants.SPACE_STR) + 1);
                    } else {
                        System.out.println("Message destination is not valid: " + namingError);
                        continue;
                    }
                } else {
                    if (messageDestination == null) {
                        System.out.println("@ a user for starting a chat. It's only needed for the first line of chat.");
                        continue;
                    }
                    content = line;
                }
            } catch (Exception e) {
                System.out.println("The chat message is not valid: " + line);
                continue;
            }
            return new SimpleMessage(name, messageDestination, content);
        }
    }

    private void initName() throws IOException {
        String inputName = null;
        while (true) {
            var message = channel.receive();
            var content = message.getContent();
            if (content.equalsIgnoreCase(Constants.CLIENT_NAME_CREATED)) {
                name = inputName;
                break;
            } else {
                System.out.println(content);
                inputName = scanner.nextLine();
                channel.send(new SimpleMessage(Constants.ANON_CLIENT, Constants.ADMIN_CLIENT, inputName));
            }
        }
    }
}
