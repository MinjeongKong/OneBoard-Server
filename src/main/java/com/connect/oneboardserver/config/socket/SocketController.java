package com.connect.oneboardserver.config.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class SocketController {

    @GetMapping("/ws/chat")
    public void connect() throws Exception{
        ZoomLauncher.launch();
    }
}
