package com.Kshitiz.blog.controllers;

import com.Kshitiz.blog.payloads.JwtAuthRequest;
import com.Kshitiz.blog.payloads.JwtAuthResponse;
import com.Kshitiz.blog.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

        System.out.println("USERNAME === " + request.getUsername());
        System.out.println("PASSWORD === " + request.getPassword());


        // 1️⃣ Authenticate username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2️⃣ Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // 3️⃣ Generate JWT token
        String token = jwtTokenHelper.generateToken(userDetails);

        // 4️⃣ Return token in response body
        JwtAuthResponse response = new JwtAuthResponse(token);

        return ResponseEntity.ok(response);
    }
}
