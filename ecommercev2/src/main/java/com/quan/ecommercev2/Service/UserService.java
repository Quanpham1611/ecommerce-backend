package com.quan.ecommercev2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Repo.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService mailService;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUserId(Integer id) {
        return userRepository.findById(id);
    }
}
