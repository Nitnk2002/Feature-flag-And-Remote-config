package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.repository.UserRepository;
import com.nitnk.FeFlagAndReConfig.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<> ("Good..", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserEntity userEntity){
        if(userEntity != null){
            userService.saveEntry(userEntity);
            return new ResponseEntity<> ("You are signup successfully..",HttpStatus.CREATED);
        }
        return new ResponseEntity<> ("Syntax Error..",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity){
        if(userEntity != null){
            if(userService.checkCredential(userEntity))
            {
                return new ResponseEntity<> ("You Logged in successfully",HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<> ("Your username or password is wrong",HttpStatus.NOT_FOUND);
    }

}
