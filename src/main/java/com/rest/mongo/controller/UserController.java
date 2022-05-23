package com.rest.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.mongo.entity.User;
import com.rest.mongo.repository.UserRepository;
import com.rest.mongo.service.UserService;


@RestController
@RequestMapping(value = "/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	// get all userinfo
	@GetMapping(value = "/users")
	public List<User> getUserinfo() {
		return userService.getAllUserInfo();
	}
	
	//get user info by id
	@GetMapping(value = "users/{userId}")
	public User getUserById(@PathVariable("userId") String userId) {
		return userService.getUserByUserId(userId);
	}
	
	
	// update user by id
	@PutMapping(value = "/users")
	public User updateUser(@PathVariable("userId") String userId, @RequestBody User users) {
		return userService.updateUserInfo(userId, users);
	}
	
	
	// create new user
	@PostMapping(value = "/users")
	public ResponseEntity<User> createUser(@RequestBody User users) {
		return ResponseEntity.status(201).body(userService.createNewUser(users));
	}
	
	
	// delete existing user
	@DeleteMapping(value = "users/{userId}")
	public void deleteUserById(@PathVariable("userId") String userId) {
		userRepository.deleteById(userId);
	}

}
