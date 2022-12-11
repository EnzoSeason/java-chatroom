package common;

import java.io.*;
import java.net.Socket;

public class SimpleChannel implements Channel {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SimpleChannel(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Constants.DEFAULT_CHARSET));
        this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Constants.DEFAULT_CHARSET));
    }

    @Override
    public void send(Message message) {
        writer.println(message.toString());
        writer.flush();
    }

    @Override
    public Message receive() throws IOException {
        String line;
        while (true) {
            line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                break;
            }
        }
        return SimpleMessage.Builder(line);
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void heartbeat() {
        // TODO study https://time.geekbang.org/course/detail/100027801-113682
    }
}
