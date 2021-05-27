package com.example.engine.api.controller;

import com.example.engine.api.dto.request.UserLoginRequestDTO;
import com.example.engine.api.dto.request.UserRequestDTO;
import com.example.engine.api.security.Role;
import com.example.engine.api.service.UserService;
import com.example.engine.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.signin(requestDTO.getUsername(), requestDTO.getPassword()));
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserRequestDTO user) {
        return userService.signup(new User(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Role.ROLE_CLIENT));
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> whoami(HttpServletRequest req) {
        return ResponseEntity.ok(userService.whoami(req));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest req) {
        return ResponseEntity.ok(userService.refresh(req.getRemoteUser()));
    }

}