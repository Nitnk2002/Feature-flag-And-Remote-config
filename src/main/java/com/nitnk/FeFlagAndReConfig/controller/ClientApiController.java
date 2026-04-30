package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.dto.response.FeatureFlagResponse;
import com.nitnk.FeFlagAndReConfig.dto.response.RemoteConfigResponse;
import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.entity.RemoteConfigEntity;
import com.nitnk.FeFlagAndReConfig.exception.ResourceNotFoundException;
import com.nitnk.FeFlagAndReConfig.services.FeatureFlagService;
import com.nitnk.FeFlagAndReConfig.services.RemoteConfigService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientApiController {

    @Autowired
    private FeatureFlagService featureFlagService;
    @Autowired
    private RemoteConfigService remoteConfigService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(
            @PathVariable String name,
            @RequestParam(required = false) String targetUserId,
            HttpServletRequest request
    ){

        String applicationId = request.getAttribute ("applicationId").toString ();
        FeatureFlagEntity featureFlagEntity = featureFlagService.findByFeatureNameAndApplicationId (name,applicationId);
        if(featureFlagEntity != null){
            boolean flagStatus = featureFlagService.isFeatureEnabledForUser (featureFlagEntity,targetUserId);

            FeatureFlagResponse flagResponse = new FeatureFlagResponse ();
            flagResponse.setFeatureName (featureFlagEntity.getFeatureName ());
            flagResponse.setEnabled (flagStatus);

            return new ResponseEntity<> (flagResponse, HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException ("Feature flag '" + name + "' not found for this application.");
        }
    }
    // Inside ClientApiController.java



    @GetMapping("/config/{key}")
    public ResponseEntity<?> getRemoteConfig(
            @PathVariable String key,
            HttpServletRequest request) {

        // 1. Get the App ID injected by your Redis ApiKeyFilter!
        String applicationId = (String) request.getAttribute("applicationId");

        // 2. Fetch from Service (which will eventually check Redis, then MongoDB)
        RemoteConfigEntity config = remoteConfigService.findByConfigKeyAndAppId(key, applicationId);

        if (config == null) {
            throw new ResourceNotFoundException("Remote Config not found for key: " + key);
        }

        // 3. Map to safe DTO
        RemoteConfigResponse responseDto = new RemoteConfigResponse(
                config.getKey(),
                config.getValue()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
