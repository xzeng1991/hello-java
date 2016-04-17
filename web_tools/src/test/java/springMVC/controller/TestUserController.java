package springMVC.controller;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import springMVC.entity.User;

public class TestUserController {
	@Test
	public void testUrlMap(){
		RestTemplate rest = new RestTemplate();
		rest.postForLocation("http://localhost:8080/web_tools/user/urlMap.htm",null);
	}
}
