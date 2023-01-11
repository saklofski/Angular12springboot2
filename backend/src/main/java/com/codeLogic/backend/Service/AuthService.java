package com.codeLogic.backend.Service;

import com.codeLogic.backend.Model.User;
import com.codeLogic.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64enocder = Base64.getUrlEncoder();

    public User register(User user) {

        //check if user with username exist or not
        if (checkUserExist(user)== true)
            return null;
        user.setToken(generateToken());
        return userRepository.save(user);
    }

    private String generateToken() {
        byte[] token = new byte[24];
        secureRandom.nextBytes(token);
        return base64enocder.encodeToString(token);
    }

    private boolean checkUserExist(User user) {
        User existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if (existingUser == null)
            return false;
        return true;
    }

    public User login(User user) {
        User existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if (existingUser.getUsername().equals(user.getUsername()) &&
                existingUser.getPassword().equals(user.getPassword()) &&
                existingUser.getRole().equals(user.getRole()))
        {
            existingUser.setPassword("");
            return existingUser;
        }
        return null;
    }
}
