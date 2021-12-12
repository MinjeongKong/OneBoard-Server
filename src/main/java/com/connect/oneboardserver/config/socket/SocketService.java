package com.connect.oneboardserver.config.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.namespace.Namespace;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SocketService implements ApplicationListener<ContextClosedEvent> {

    public static final String EVENT_NAME_INIT = "init";
    public static final String EVENT_NAME_REQ_ATTENDANCE = "attendance request";
    public static final String EVENT_NAME_REQ_UNDERSTANDING = "understanding request";
    public static final String EVENT_NAME_RES_UNDERSTANDING = "understanding response";
    public static final String EVENT_NAME_REQ_QUIZ = "quiz request";
    public static final String EVENT_NAME_RES_QUIZ = "quiz response";

    private Configuration config;
    private SocketIOServer server;
    private Namespace mainNamespace;

    private ConcurrentMap<String, UUID> roomHosts = new ConcurrentHashMap<>();

    public SocketService() {
        config = new Configuration();
        config.setHostname(getServerIp());
        config.setPort(8070);

        server = new SocketIOServer(config);
        mainNamespace = (Namespace) server.getNamespace("");
    }

    private String getServerIp() {
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
        }
    }

    public void startSocketIOServer() {
        addConnectListener();
        addInitEventListener();
        addDisconnectListener();

        server.start();
    }

    private void addInitEventListener() {
        server.addEventListener(EVENT_NAME_INIT, InitObject.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient client, InitObject data, AckRequest ackRequest) {
//                System.out.println("==============================");
//                System.out.println("userType = " + data.getUserType());
//                System.out.println("session = " + data.getRoom());

                mainNamespace.joinRoom(data.getRoom(), client.getSessionId());

//                System.out.println("Room :");
//                for (String room : mainNamespace.getRooms()) {
//                    System.out.println("\t" + room);
//                }
//                System.out.println("Room Clients :");
//                for (SocketIOClient roomClient : mainNamespace.getRoomClients(data.getRoom())) {
//                    System.out.println("\t" + roomClient.getSessionId());
//                }

                if (data.getUserType().equals("T")) {
                    roomHosts.putIfAbsent(data.getRoom(), client.getSessionId());
                }
//                System.out.println("roomHosts : " + roomHosts);

//                client.sendEvent("echo", "userType : " + data.getUserType() + " room : " + data.getRoom());
            }
        });
    }

    private void addConnectListener() {
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Socket Connected : " + client.getSessionId());
//                server.getBroadcastOperations().sendEvent("new connection", "new client : " + client.getSessionId());
            }
        });
    }

    private void addDisconnectListener() {
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                if (roomHosts.containsValue(client.getSessionId())) {
                    roomHosts.remove(getKeyByValue(client));
                }
            }
        });
    }
    
    private String getKeyByValue(SocketIOClient client) {
        String result = null;
        for (Map.Entry<String, UUID> entry : roomHosts.entrySet()) {
            if (entry.getValue().equals(client.getSessionId())) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    public void stopSocketIOServer() {
        server.stop();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("애플리케이션이 종료되어 소켓 연결을 중지합니다");
        stopSocketIOServer();
    }

    public void sendAttendanceRequestEvent(String room) {
        for (SocketIOClient roomClient : mainNamespace.getRoomClients(room)) {
            if(roomClient.getSessionId().equals(roomHosts.get(room))) {
                continue;
            }
            roomClient.sendEvent(EVENT_NAME_REQ_ATTENDANCE, "professor's attendance check request");
        }
    }

    public void sendUnderstandingRequestEvent(String room) {
        for (SocketIOClient roomClient : mainNamespace.getRoomClients(room)) {
            if (roomClient.getSessionId().equals(roomHosts.get(room))) {
                continue;
            }
            roomClient.sendEvent(EVENT_NAME_REQ_UNDERSTANDING, "professor's understanding check request");
        }
    }

    public void sendUnderstandingResponseEvent(String room) {
        mainNamespace.getClient(roomHosts.get(room)).sendEvent(EVENT_NAME_RES_UNDERSTANDING, "student's response for understanding check request");
    }

    public void sendQuizRequestEvent(String room) {
        for (SocketIOClient roomClient : mainNamespace.getRoomClients(room)) {
            if (roomClient.getSessionId().equals(roomHosts.get(room))) {
                continue;
            }
            roomClient.sendEvent(EVENT_NAME_REQ_QUIZ, "professor's quiz request");
        }
    }

    public void sendQuizResponseEvent(String room) {
        mainNamespace.getClient(roomHosts.get(room)).sendEvent(EVENT_NAME_RES_QUIZ, "student's response for quiz request");
    }
}
