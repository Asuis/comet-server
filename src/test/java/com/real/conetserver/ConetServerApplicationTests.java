package com.real.conetserver;

import com.real.conetserver.tunnel.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConetServerApplicationTests {
	@Autowired
	private RoomService roomService;
	@Test
	public void contextLoads() {

	}

}
