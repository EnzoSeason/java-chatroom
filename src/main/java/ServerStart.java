import common.Constants;
import server.SimpleServer;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) {
        try {
            var server = new SimpleServer(Constants.SERVER_PORT);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
