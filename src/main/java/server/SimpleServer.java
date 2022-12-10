package server;

import common.Channel;
import common.SimpleClientNameHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServer implements Server {

    private int port;
    private ExecutorService service = Executors.newCachedThreadPool();
    private ConcurrentHashMap<String, Channel> platform = new ConcurrentHashMap<>();

    public SimpleServer(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        System.out.println("Simple Chat Room Server starts");
        var ss = new ServerSocket(port);
        while (true) {
            var socket = ss.accept();
            service.submit(new SimpleServerTask(platform, socket, new SimpleClientNameHelper()));
        }
    }
}
