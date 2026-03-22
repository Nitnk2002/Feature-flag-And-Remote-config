package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.services.FeatureFlagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/feature")
public class FeatureFlagController {

    @Autowired
    private FeatureFlagService featureFlagService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FeatureFlagEntity featureFlagEntity){


        if(featureFlagService.saveFeature (featureFlagEntity)){
            return new ResponseEntity<> ("Feature Create Successfully..", HttpStatus.CREATED);
        }
        return new ResponseEntity<> ("Error During creation!!!",HttpStatus.BAD_REQUEST);
    }

}
