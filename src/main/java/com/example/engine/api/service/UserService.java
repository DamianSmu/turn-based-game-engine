package com.example.engine.api.service;

import com.example.engine.api.dto.response.TokenResponseDTO;
import com.example.engine.api.exception.ResponseException;
import com.example.engine.api.repository.UserRepository;
import com.example.engine.api.security.JwtTokenProvider;
import com.example.engine.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, Collections.singletonList(userRepository.findByUsername(username).orElseThrow(() -> new ResponseException("User not found in db", HttpStatus.BAD_REQUEST)).getRole()));
        } catch (AuthenticationException e) {
            throw new ResponseException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public TokenResponseDTO signup(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            String token = jwtTokenProvider.createToken(user.getUsername(), Collections.singletonList(user.getRole()));
            String id = user.getId();
            return new TokenResponseDTO(token, id);
        } else {
            String id = userRepository.findByUsername(user.getUsername()).get().getId();
            String token = signin(user.getUsername(), user.getPassword());
            return new TokenResponseDTO(token, id);
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }


    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).orElseThrow(() -> new ResponseException("User not found in db", HttpStatus.BAD_REQUEST));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, Collections.singletonList(userRepository.findByUsername(username).orElseThrow(() -> new ResponseException("User not found in db", HttpStatus.BAD_REQUEST)).getRole()));
    }

    public User getUser(Authentication authentication) {
        String name = authentication.getName();
        return userRepository.findByUsername(name).orElseThrow(() -> new ResponseException("User not found in db", HttpStatus.BAD_REQUEST));
    }
}
