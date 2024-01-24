package com.billingusers.controller;

import java.util.List;

import com.billingusers.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.billingusers.entity.LoginRequest;
import com.billingusers.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User savedUser = userService.createUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@GetMapping("/getallusers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/getuserbyid/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/getuserbyname/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String userName) {
		User user = userService.getUserByUsername(userName);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String userId, @RequestBody User user) {
		user.setId(userId);
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
	}

	@PostMapping("/token")
	public String getToken(@RequestBody LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			String res = userService.generateToken(loginRequest.getUserName());
			String result = "{\"token\":\"" + res + "\"}";
			return result;
			//return userService.generateToken(loginRequest.getUserName());
		} else {
			throw new RuntimeException("invalid access");
		}
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		userService.validateToken(token);
		return "Token is valid";
	}

	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		return new ResponseEntity<>(userService.forgotPassword(email),HttpStatus.OK);
	}

	@PutMapping("/set-password")
	public ResponseEntity<String> setpassword(@RequestParam String email, @RequestHeader String newPassword){
		return new ResponseEntity<>(userService.setpassword(email, newPassword),HttpStatus.OK);
		}
}
//	@PostMapping("/authenticate")
//	public String authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
//		if (authentication.isAuthenticated()) {
//			String res = jwtService.generateToken(loginRequest.getUserName());
//			String result = "{\"token\":\"" + res + "\"}";
//			return result;
//		} else {
//			throw new UsernameNotFoundException("Invalid username or password");
//		}
//	}

