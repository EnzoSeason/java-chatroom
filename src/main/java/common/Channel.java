package common;

import java.io.IOException;

public interface Channel {
    void send(Message message);
    Message receive() throws IOException;
    void close();
}
