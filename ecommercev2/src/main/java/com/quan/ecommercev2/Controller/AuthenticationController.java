package com.quan.ecommercev2.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quan.ecommercev2.DTO.Request.AuthenticationRequest;
import com.quan.ecommercev2.DTO.Request.RegisterRequest;
import com.quan.ecommercev2.DTO.Response.AuthenticationResponse;
import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Repo.UserRepository;
import com.quan.ecommercev2.Service.AuthenticationService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) throws MessagingException {
        String message = authenticationService.registerUser(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String verificationToken) {

        Optional<User> optionalUser = userRepository.findByLink(verificationToken);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }

        User user = optionalUser.get();
        user.setStatus(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Your email has been verified. You can now log in.");
    }

}
