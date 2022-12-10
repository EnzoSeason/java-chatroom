package common;

import java.io.IOException;
import java.net.Socket;

public class SimpleChannel implements Channel {
    private Socket socket;

    public SimpleChannel(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void send(Message message) {

    }

    @Override
    public Message receive() throws IOException {
        return null;
    }

    @Override
    public void close() {

    }
}
