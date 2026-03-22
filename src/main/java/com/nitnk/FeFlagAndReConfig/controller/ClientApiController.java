package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.services.FeatureFlagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientApiController {

    @Autowired
    private FeatureFlagService featureFlagService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name, HttpServletRequest request){
        String applicationId = request.getAttribute ("applicationId").toString ();
        FeatureFlagEntity featureFlagEntity = featureFlagService.findByFeatureNameAndApplicationId (name,applicationId);
        if(featureFlagEntity != null){
            return new ResponseEntity<> (featureFlagEntity, HttpStatus.OK);
        }
        return new ResponseEntity<> ("Feature Flag not found for this application Name",HttpStatus.NOT_FOUND);
    }
}
