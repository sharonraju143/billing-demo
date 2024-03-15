package com.billingreports.serviceimpl.user;

import com.billingreports.entities.user.User;
import com.billingreports.exceptions.InvalidUserException;
import com.billingreports.exceptions.UserNotFoundException;
import com.billingreports.exceptions.UsernameOrEmailAlreadyExistsException;
import com.billingreports.repositories.user.UserRepository;
import com.billingreports.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        // Check if the username already exists in the database
        if (emailOrUserNameAlreadyExist(user)) {
            throw new UsernameOrEmailAlreadyExistsException();
        }

        // Proceed with user creation if the username doesn't exist
        user.setRoles("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        user.setActive(true);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public User getUserByUsername(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUserName(user.getUserName());
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(String userId) {
        User user = this.getUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }
    @Override
    public boolean emailOrUserNameAlreadyExist(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByUserName(user.getUserName()).isPresent();
    }


    @Override
    public String changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findByUserName(getCurrentUsername()).orElseThrow(()->new UserNotFoundException());
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new InvalidUserException();
        }
        else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password changed successfully";
        }
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Returns the username of the authenticated user
        }
        return null; // No user authenticated or anonymous user
    }

    public String forgotPassword(String email) {
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        String randomPassword = generateRandomPassword();
        System.out.println(randomPassword);
        user.setPassword(passwordEncoder.encode(randomPassword));
        userRepository.save(user);
        return "You have received a mail.";
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        int passwordLength = 6 + random.nextInt(7); // Generates a random length between 6 and 12

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }
}
