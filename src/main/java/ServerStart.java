import common.Constants;
import server.SimpleServer;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        var server = new SimpleServer(Constants.SERVER_PORT);
        server.start();
    }
}
