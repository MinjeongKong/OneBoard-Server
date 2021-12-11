package com.connect.oneboardserver;

import com.connect.oneboardserver.config.socket.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OneboardServerApplication {

	@Autowired
	private SocketManager socketManager;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(OneboardServerApplication.class, args);

		OneboardServerApplication main = context.getBean(OneboardServerApplication.class);
		main.socketManager.startSocketIOServer();
	}

}
