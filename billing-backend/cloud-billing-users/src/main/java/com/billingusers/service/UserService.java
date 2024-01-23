package com.billingusers.service;

import java.util.List;

import com.billingusers.entity.User;

public interface UserService {

	public User createUser(User user);

	public User getUserById(String userId);

	public User getUserByEmail(String email);

	public User getUserByUsername(String userName);

	public List<User> getAllUsers();

	public User updateUser(User user);

	public void deleteUser(String userId);

	public boolean emailOrUserNameAlreadyExist(User user);
	
	public void validateToken(String token);
	
	public String generateToken(String username);

}