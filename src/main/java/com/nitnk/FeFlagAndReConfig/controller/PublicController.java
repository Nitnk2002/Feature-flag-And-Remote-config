package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.dto.request.UserSignupRequest;
import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.repository.UserRepository;
import com.nitnk.FeFlagAndReConfig.services.UserService;
import com.nitnk.FeFlagAndReConfig.utils.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("health")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<> ("Good..", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest user){
        if(user != null){
            userService.saveEntry(user);
            return new ResponseEntity<> ("You are signup successfully..",HttpStatus.CREATED);
        }
        return new ResponseEntity<> ("Syntax Error..",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSignupRequest user){
        try{
            authenticationManager.authenticate (
                    new UsernamePasswordAuthenticationToken (user.getUsername (),user.getPassword ())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername (user.getUsername ());
            String jwt = jwtUtil.generateToken (userDetails.getUsername ());
            if(jwt != null){
                    return new ResponseEntity<> ("You Logged in successfully "+"\ntoken : "+jwt,HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<> ("Your username or password is wrong",HttpStatus.NOT_FOUND);
        }
        return null;
    }

}
