package com.rest.mongo.controller.integrationTest;

import com.rest.mongo.UserApplication;
import com.rest.mongo.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = UserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getAllUserIntTest()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User[] body = restTemplate.exchange("http://localhost:"+port  + "/api/users", HttpMethod.GET, entity,User[].class).getBody();
        System.out.println(body.length);
    }

    @Test
    public void addUserIntTest() {
        User user = new User("123","user-1");
        ResponseEntity<User> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/users", user,User.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getBody().getUserName());
    }

}