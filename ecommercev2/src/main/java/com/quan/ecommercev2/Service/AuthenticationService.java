package com.quan.ecommercev2.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quan.ecommercev2.DTO.Request.AuthenticationRequest;
import com.quan.ecommercev2.DTO.Request.RegisterRequest;
import com.quan.ecommercev2.DTO.Response.AuthenticationResponse;
import com.quan.ecommercev2.Model.Role;
import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Repo.TokenRepository;
import com.quan.ecommercev2.Repo.UserRepository;
import com.quan.ecommercev2.Token.Token;
import com.quan.ecommercev2.Token.TokenType;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired

    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired

    private EmailService emailService;
    @Autowired
    private CartService cartService;

    public String registerUser(RegisterRequest request) throws MessagingException {
        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists.");
        }
        String verificationToken = UUID.randomUUID().toString();

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .status(false)
                .link(verificationToken)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationToken);
        return "Verify you email";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.getStatus() == false) {
            throw new RuntimeException("User Didn't verify");
        }
        cartService.getCart(user.getId());
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
