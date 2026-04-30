package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.dto.request.CreateRemoteConfigRequest;
import com.nitnk.FeFlagAndReConfig.dto.response.RemoteConfigResponse;
import com.nitnk.FeFlagAndReConfig.entity.RemoteConfigEntity;
import com.nitnk.FeFlagAndReConfig.repository.RemoteConfigRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoteConfigService {

    @Autowired
    private RemoteConfigRepository remoteConfigRepository;
    @Autowired
    private RedisService redisService;

    private String getRedisKey(String applicationId, String configKey) {
        return "config:" + applicationId + ":" + configKey;
    }

    public RemoteConfigEntity findByConfigKeyAndAppId(String key, String applicationId) {
        String redisKey = getRedisKey (applicationId, key);
        RemoteConfigEntity remoteConfigEntity = redisService.get (redisKey,RemoteConfigEntity.class);
        if(remoteConfigEntity != null){
            return remoteConfigEntity;
        }else{
            RemoteConfigEntity configEntity = remoteConfigRepository.findByKeyAndApplicationId (key, applicationId);
            if (configEntity != null) {
                redisService.set (redisKey, configEntity, 86400L);
            }
            return configEntity;
        }
    }

    public boolean saveConfig(RemoteConfigEntity newConfig) {
        if (newConfig == null) return false;

        RemoteConfigEntity existing = remoteConfigRepository.findByKeyAndApplicationId(
                newConfig.getKey(),
                newConfig.getApplicationId()
        );
        if (existing != null) {
            return false;
        }

        remoteConfigRepository.save(newConfig);
        return true;
    }

    public boolean updateConfig(@Valid CreateRemoteConfigRequest request, String oldConfigKey) {
        RemoteConfigEntity remoteConfigEntity = remoteConfigRepository.findByKeyAndApplicationId (oldConfigKey,request.getApplicationId ());
        if(remoteConfigEntity != null ){
            redisService.delete(getRedisKey(request.getApplicationId(), oldConfigKey));
            remoteConfigEntity.setKey (request.getConfigKey ());
            remoteConfigEntity.setValue (request.getConfigValue ());
            remoteConfigEntity.setDescription (request.getDescription ());
            remoteConfigRepository.save (remoteConfigEntity);
            return true;
        }
        return false;
    }

    public boolean deleteConfig(String configKey, String applicationId) {
        RemoteConfigEntity remoteConfigEntity = remoteConfigRepository.findByKeyAndApplicationId (configKey,applicationId);
        String redisKey = getRedisKey (applicationId,configKey);
        if(remoteConfigEntity != null ){
            redisService.delete (redisKey);
            remoteConfigRepository.delete (remoteConfigEntity);
            return true;
        }
        return false;
    }

    // Add this method inside your RemoteConfigService.java
    public List<RemoteConfigEntity> getAllConfigs() {
        // This uses Spring Data MongoDB's built-in findAll() method
        return remoteConfigRepository.findAll();
    }
}
