package com.nitnk.FeFlagAndReConfig.controller;

import com.nitnk.FeFlagAndReConfig.entity.RemoteConfigEntity;
import com.nitnk.FeFlagAndReConfig.services.RemoteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// ⚠️ Combined with your global context path, this becomes /api/config
@RequestMapping("/config")
public class AdminConfigController {

    @Autowired
    private RemoteConfigService remoteConfigService;

    // This creates the exact endpoint React is looking for: /api/config/all
    @GetMapping("/all")
    public ResponseEntity<List<RemoteConfigEntity>> getAllConfigs(@RequestParam String appId) {

        // Fetch all configs from the database
        List<RemoteConfigEntity> allConfigs = remoteConfigService.getAllConfigs(appId);

        // Return them to the frontend with a 200 OK status
        return ResponseEntity.ok(allConfigs);
    }
}
