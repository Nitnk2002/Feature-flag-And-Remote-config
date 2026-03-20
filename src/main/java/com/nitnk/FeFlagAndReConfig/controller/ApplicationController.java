package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import com.nitnk.FeFlagAndReConfig.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<?> createApplication(@RequestBody ApplicationEntity applicationEntity){
        String apiKey = applicationService.saveApplication (applicationEntity);
        if(apiKey != null){
            return new ResponseEntity<> (apiKey, HttpStatus.CREATED);
        }
        return new ResponseEntity<> ("Error during saving Application data...!!",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> searchByUserId(@PathVariable String userId){
        ApplicationEntity applicationEntity = applicationService.searchByUserId (userId);

        if (applicationEntity != null){
            return new ResponseEntity<> (applicationEntity,HttpStatus.OK);
        }
        return new ResponseEntity<> ("Error Getting Application Data|||",HttpStatus.BAD_REQUEST);
    }

}
