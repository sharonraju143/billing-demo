package com.billingreports.serviceimpl.user;

import com.billingreports.entities.user.User;
import com.billingreports.exceptions.ResourceNotFoundException;
import com.billingreports.exceptions.UsernameAlreadyExistsException;
import com.billingreports.repositories.user.UserRepository;
import com.billingreports.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new UsernameAlreadyExistsException("Username already exists");
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId Not Found in Resource"+userId));
        return user;
    }

    @Override
    public User getUserByUsername(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException("User not found by " + userName));
        return user;
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist by id " + user.getId()));
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

//    @Override
//    public void changePassword(ChangePassword password, Principal connectedUser) {
//        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//        if (!passwordEncoder.matches(password.getCurrentPassword(),user.getPassword())){
//            throw new IllegalStateException("Wrong Password");
//        }
//        if(!password.getNewPassword().equals(password.getConfirmNewPassword())){
//            throw new IllegalStateException("Password are not same");
//        }
//        user.setPassword(passwordEncoder.encode(password.getNewPassword()));
//        userRepository.save(user);
//    }
}
