package com.eldelivery.server;

import com.eldelivery.server.Controllers.Order.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerApplicationTests {

	@Autowired
	private OrderController controller;

	@LocalServerPort
	private int port;



	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}

}
