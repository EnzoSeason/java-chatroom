import client.SimpleClient;

import java.io.IOException;

public class ClientStart {
    public static void main(String[] args) {
        try {
            var server = args.length > 0 ? args[0] : "1227.0.0.1";
            var client = new SimpleClient(server);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
