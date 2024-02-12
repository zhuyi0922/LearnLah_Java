package com.team4.adproject.Service;

import com.team4.adproject.Model.User;
import com.team4.adproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    public boolean login(String username, String password) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public boolean register(String username, String password) {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return false;
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
