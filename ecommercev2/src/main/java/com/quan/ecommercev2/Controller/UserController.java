package com.quan.ecommercev2.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Service.EmailService;
import com.quan.ecommercev2.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService mailService;
    @GetMapping
    public  ResponseEntity<User> getUserDetailsById(@RequestParam Integer id) {
        Optional<User> user= userService.findByUserId(id);
        return ResponseEntity.ok(user.get());
    }
}
