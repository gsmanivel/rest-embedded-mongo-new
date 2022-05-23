package com.rest.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.mongo.entity.User;
import com.rest.mongo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUserInfo() {
		List<User> userList = new ArrayList<User>();
		return userRepository.findAll();
	}

	public User getUserByUserId(String userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User updateUserInfo(String userId, User users) {
		User existingUserId = userRepository.findById(userId).orElse(null);
		existingUserId.setUserName(users.getUserName());
		return userRepository.save(existingUserId);
	}

	public User createNewUser(User users) {
		return userRepository.save(users);
	}

}
