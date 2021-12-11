package com.connect.oneboardserver.config.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.namespace.Namespace;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SocketManager implements ApplicationListener<ContextClosedEvent> {

    private Configuration config;
    private SocketIOServer server;

    private ConcurrentMap<String, UUID> roomHosts = new ConcurrentHashMap<>();

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

        server.addEventListener("init connection", InitObject.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient client, InitObject data, AckRequest ackRequest) {
                System.out.println("userType = " + data.getUserType());
                System.out.println("session = " + data.getRoom());

                Namespace mainNamespace = (Namespace) server.getNamespace("");

                mainNamespace.joinRoom(data.getRoom(), client.getSessionId());

//                System.out.println("Room :");
//                for (String room : mainNamespace.getRooms()) {
//                    System.out.println("\t" + room);
//                }
//                System.out.println(data.getRoom() + " Room Clients :");
//                for (SocketIOClient roomClient : mainNamespace.getRoomClients(data.getRoom())) {
//                    System.out.println("\t" + roomClient.getSessionId());
//                }

                if (data.getUserType().equals("T")) {
                    roomHosts.putIfAbsent(data.getRoom(), client.getSessionId());
//                    System.out.println("roomHosts : " + roomHosts);
                }

                client.sendEvent("echo", "userType : " + data.getUserType() + " room : " + data.getRoom());
            }
        });

        server.start();
    }

    public void stopSocketIOServer() {
        server.stop();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("애플리케이션이 종료되어 소켓 연결을 중지합니다");
        stopSocketIOServer();
    }
}
