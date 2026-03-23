package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import com.nitnk.FeFlagAndReConfig.exception.ResourceNotFoundException;
import com.nitnk.FeFlagAndReConfig.services.ApplicationService;
import com.nitnk.FeFlagAndReConfig.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createApplication(@RequestBody ApplicationEntity applicationEntity){
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        String username = auth.getName ();
        String userId = userService.getUserId (username);
        applicationEntity.setUserId (userId);
        String apiKey = applicationService.saveApplication (applicationEntity);
        if(apiKey == null){
            return new ResponseEntity<> ("Error during saving Application data...!!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (apiKey, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> searchByUserId(@PathVariable String userId){
        ApplicationEntity applicationEntity = applicationService.searchByUserId (userId);

        if (applicationEntity != null){
            return new ResponseEntity<> (applicationEntity,HttpStatus.OK);
        }
        return new ResponseEntity<> ("Error Getting Application Data|||",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api-key")
    public ResponseEntity<?> getApiKey(@RequestParam String appName){
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        String username = auth.getName ();
        String userId = userService.getUserId (username);
        ApplicationEntity applicationEntity = applicationService.getApiKey (userId,appName);
        if(applicationEntity == null){
            throw new ResourceNotFoundException ("ERROR : Getting key for " + appName);
        }
        return new ResponseEntity<> ("API KEY : "+applicationEntity.getApiKey (),HttpStatus.OK);
    }

    @PostMapping("/new-api-key")
    public ResponseEntity<?> generateNewApiKey(@RequestParam String appName){
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        String username = auth.getName ();
        String userId = userService.getUserId (username);
        String apiKey = applicationService.generateNewApiKey (userId,appName);
        if(apiKey == null){
            throw new ResourceNotFoundException ("ERROR : Creating New Api Key for "+appName);
        }
        return new ResponseEntity<> ("API-KEY : "+apiKey,HttpStatus.OK);
    }

}
