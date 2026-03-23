package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import com.nitnk.FeFlagAndReConfig.utils.ApiKeyGenerator;
import com.nitnk.FeFlagAndReConfig.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public String saveApplication(ApplicationEntity applicationEntity){
        if(applicationEntity != null){
            applicationEntity.setApiKey (ApiKeyGenerator.generateApiKey ());
            applicationRepository.save (applicationEntity);
            return applicationEntity.getApiKey ();
        }
        return null;
    }

    public ApplicationEntity searchByUserId(String userId){
        ApplicationEntity applicationEntity =  applicationRepository.findByUserId (userId);
        if(applicationEntity != null){
            return applicationEntity;
        }
        return null;
    }

    public ApplicationEntity getApiKey(String userId,String appName){
        if(userId != null && appName != null){
            return applicationRepository.findByUserIdAndAppName (userId,appName);
        }
        return null;
    }

    public String generateNewApiKey(String userId,String appName){
        ApplicationEntity applicationEntity = applicationRepository.findByUserIdAndAppName (userId,appName);
        if(applicationEntity != null){
            applicationEntity.setApiKey (ApiKeyGenerator.generateApiKey ());
            applicationRepository.save (applicationEntity);
            return applicationEntity.getApiKey ();
        }
        return null;
    }
}
