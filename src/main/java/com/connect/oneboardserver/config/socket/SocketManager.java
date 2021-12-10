package com.connect.oneboardserver.config.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.stereotype.Component;

@Component
public class SocketManager {

    private Configuration config;
    private SocketIOServer server;

    public SocketManager() {
        config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8070);

        server = new SocketIOServer(config);
    }

    public void startSocketIOServer() {

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Socket Connected : " + client.getSessionId());
                server.getBroadcastOperations().sendEvent("new connection", "new client : " + client.getSessionId());
            }
        });

        server.addEventListener("init", ChatObject.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                System.out.println("userName : " + data.getUserName());
                System.out.println("message : " + data.getMessage());

                client.sendEvent("echo", data.getMessage());
            }
        });

        server.start();
    }

    public void stopSocketIOServer() {
        server.stop();
    }
}
