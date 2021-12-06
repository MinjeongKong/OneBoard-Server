package com.connect.oneboardserver.config.socket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;


public class ZoomLauncher{
    public static void launch() throws Exception {
        Configuration config = new Configuration();
        config.setHostname("200.200.5.88");
        config.setPort(8070);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("Hello!", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient client, Object data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("Hello!", "test server msg");
                System.out.println(data);
            }
        });

        System.out.println("111111111111111111111111111111111");
        server.start();

        System.out.println("2222222222222222222222222222222222");
        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("3333333333333333333333333333333333");
        server.stop();
    }
}
