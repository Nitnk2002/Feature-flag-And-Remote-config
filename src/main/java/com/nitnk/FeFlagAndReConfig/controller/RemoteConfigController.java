package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.dto.request.CreateRemoteConfigRequest;
import com.nitnk.FeFlagAndReConfig.entity.RemoteConfigEntity;
// import your RemoteConfigService here
import com.nitnk.FeFlagAndReConfig.exception.ResourceNotFoundException;
import com.nitnk.FeFlagAndReConfig.services.RemoteConfigService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remote-config")
public class RemoteConfigController {

    @Autowired
    private RemoteConfigService remoteConfigService;

    @GetMapping("/all")
    public ResponseEntity<List<RemoteConfigEntity>> getAllConfigs(@RequestParam String appId) {

        // Fetch all configs from the database
        List<RemoteConfigEntity> allConfigs = remoteConfigService.getAllConfigs(appId);

        // Return them to the frontend with a 200 OK status
        return ResponseEntity.ok(allConfigs);
    }

    // 1. CREATE
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRemoteConfigRequest request) {

        RemoteConfigEntity newConfig = new RemoteConfigEntity();
        newConfig.setKey(request.getConfigKey());
        newConfig.setValue(request.getConfigValue());
        newConfig.setDescription(request.getDescription());
        newConfig.setApplicationId(request.getApplicationId());

        if (remoteConfigService.saveConfig(newConfig)) {
            return new ResponseEntity<>("Remote Config Created Successfully.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Config Key already exists for this application.", HttpStatus.BAD_REQUEST);
    }

    // 2. UPDATE
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @Valid @RequestBody CreateRemoteConfigRequest request,
            @RequestParam String oldConfigKey) {

        if (remoteConfigService.updateConfig(request, oldConfigKey)) {
            return new ResponseEntity<>("Remote Config Updated Successfully", HttpStatus.OK);
        }
        // Utilizing our global exception handler!
        throw new ResourceNotFoundException("Remote Config Not Found");
    }

    // 3. DELETE
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam String configKey,
            @RequestParam String applicationId) {

        if (remoteConfigService.deleteConfig(configKey, applicationId)) {
            return new ResponseEntity<>("Remote Config Deleted Successfully", HttpStatus.OK);
        }
        throw new ResourceNotFoundException ("Remote Config Not Found");
    }
}