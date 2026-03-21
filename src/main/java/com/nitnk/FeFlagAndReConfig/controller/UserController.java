package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("health")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<> ("Good..", HttpStatus.OK);
    }

    @GetMapping("/user-data/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        UserEntity userEntity = userService.getUser (username);
        if(userEntity != null){
            return new ResponseEntity<> (userEntity,HttpStatus.OK);
        }
        return new ResponseEntity<> ("ERROR while getting user",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){
        if(userService.updateUser (userEntity)){
            return new ResponseEntity<> ("User Date Updated successfully..",HttpStatus.OK);
        }
        return new ResponseEntity<> ("Error while updating...",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        if(userService.deleteUser (username)){
            return new ResponseEntity<> (username+" User Deleted Successfully..",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<> ("ERROR while deleting user",HttpStatus.BAD_REQUEST);
    }
}
