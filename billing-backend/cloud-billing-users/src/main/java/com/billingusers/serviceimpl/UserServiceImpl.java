package com.billingusers.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.billingusers.exceptions.EmailOrUserNameAlreadyExist;
import com.billingusers.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.billingusers.entity.User;
import com.billingusers.exceptions.ResourceNotFoundException;
import com.billingusers.jwtservice.JwtService;
import com.billingusers.repository.UserRepository;
import com.billingusers.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private EmailUtil emailUtil;

//	@Autowired
//	private EmailUtil emailUtil;
//	@Autowired
//	private ModelMapper modelMapper;



	@Override
	public User createUser(User user) {
	    // Check if the username already exists in the database
//	    if (userRepository.existsByUserName(user.getUserName())) {
//	        throw new UsernameAlreadyExistsException("Username already exists");
//	    }
		if(emailOrUserNameAlreadyExist(user)) {
			throw new EmailOrUserNameAlreadyExist();
		}
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setId(UUID.randomUUID().toString());
	    user.setActive(true);
	    
	    User savedUser = userRepository.save(user);
	    return savedUser;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.filter(User::isActive) // Filter only active users
				.collect(Collectors.toList());

	}

	@Override
	public User getUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist by id " + userId));
		return user;
	}

	@Override
	public User getUserByUsername(String userName) {
		Optional<User> optionalUser = userRepository.findByUserName(userName);
		User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found by username " + userName));
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User optionalUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
		return optionalUser;
	}

	@Override
	public User updateUser(User user) {
		User existingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist by id " + user.getId()));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		User updatedUser = userRepository.save(existingUser);
		return updatedUser;
	}

	@Override
	public void deleteUser(String userId) {
		User user = this.getUserById(userId);
		user.setActive(false);
		userRepository.save(user);
	}

	public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

	@Override
	public String forgotPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("User not found with this email: "+email));
		try {
			emailUtil.sendSetPasswordEmail(email);
		}catch (MessagingException e){
			throw new RuntimeException("Unable to send set password email please try again");
		}
		return "please check your email to set new password to your account";
	}

	@Override
	public String setpassword(String email, String newPassword) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("User not found with this email: "+email));
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return "New password set successfully now Login with new password";
	}

	public void validateToken(String token) {
        jwtService.validateToken(token);
    }

	@Override
	public boolean emailOrUserNameAlreadyExist(User user) {
		return userRepository.findByEmail(user.getEmail()).isPresent() ||
				userRepository.findByUserName(user.getUserName()).isPresent();
	}

}