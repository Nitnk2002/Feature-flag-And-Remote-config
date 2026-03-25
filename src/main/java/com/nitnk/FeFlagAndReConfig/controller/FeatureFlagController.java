package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.dto.request.CreateFeatureRequest;
import com.nitnk.FeFlagAndReConfig.dto.response.FeatureFlagResponse;
import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.exception.ResourceNotFoundException;
import com.nitnk.FeFlagAndReConfig.services.FeatureFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/feature")
public class FeatureFlagController {

    @Autowired
    private FeatureFlagService featureFlagService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateFeatureRequest feature){

        if(featureFlagService.saveFeature (feature)){
            return new ResponseEntity<> ("Feature Create Successfully..", HttpStatus.CREATED);
        }
        return new ResponseEntity<> ("Error During creation!!!",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String featureName,@RequestParam String applicationId){
        FeatureFlagEntity flag = featureFlagService.findByFeatureNameAndApplicationId (featureName,applicationId);
        if(flag == null){
            throw new ResourceNotFoundException ("Feature flag for Feature Name : "+featureName);
        }
        FeatureFlagResponse flagResponse = new FeatureFlagResponse ();
        flagResponse.setFeatureName (flag.getFeatureName ());
        flagResponse.setEnabled (flag.isEnabled ());

        return new ResponseEntity<> (flagResponse,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(
            @Validated @RequestBody CreateFeatureRequest featureRequest,
            @RequestParam String applicationId,
            @RequestParam String oldFeatureName){
        if(featureFlagService.updateFeature(featureRequest,applicationId,oldFeatureName)){
            return new ResponseEntity<> ("Feature Updated Successfully",HttpStatus.OK);
        }
        throw new ResourceNotFoundException ("Feature Flag Not Found Exception");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String featureName,@RequestParam String applicationId){
        if(featureFlagService.deleteFeature(featureName,applicationId)){
            return new ResponseEntity<> (featureName+" is Deleted Successfully..",HttpStatus.NO_CONTENT);
        }
        throw new ResourceNotFoundException ("Error during deleting "+featureName+" !");
    }

}
