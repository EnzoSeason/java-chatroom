package common;

import java.io.IOException;

public interface Channel {
    void send(Message message);
    Message receive() throws IOException;
    void close();

    /**
     * Check the communication between the server and the client
     * If no communication for a while, close the channel
     */
    void heartbeat();
}
