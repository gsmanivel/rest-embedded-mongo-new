package com.rest.mongo.repository;

import com.rest.mongo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest(properties = {"spring.mongodb.embedded.version=3.4.5"})
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest   {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setUserName("user-1");
        user1.setUserId("1234");

        User user2 = new User();
        user2.setUserName("user-2");
        user2.setUserId("1235");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void tearDown() {
        System.out.println(userRepository.findAll().size());
         userRepository.deleteAll();
        System.out.println(userRepository.findAll().size());
    }

    @Test
    public void getUser(){
        List<User> userList=  userRepository.findAll();
        assertEquals(0,userList.size());
    }

    @Test
    public void getUserPositive(){
        List<User> userList=  userRepository.findAll();
        assertEquals(2,userList.size());
    }
}