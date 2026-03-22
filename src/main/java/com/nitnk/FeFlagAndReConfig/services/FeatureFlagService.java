package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.dto.request.CreateFeatureRequest;
import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    public boolean saveFeature(CreateFeatureRequest feature){
        if(feature != null && feature.getApplicationId () != null){
            FeatureFlagEntity flagEntity = new FeatureFlagEntity ();
            flagEntity.setFeatureName (feature.getFeatureName ());
            flagEntity.setEnabled (feature.isEnabled ());
            flagEntity.setApplicationId (feature.getApplicationId ());
            flagEntity.setRolloutPercentage (feature.getRolloutPercentage ());
            featureFlagRepository.save (flagEntity);
            return true;
        }
        return false;
    }

    public FeatureFlagEntity findByFeatureNameAndApplicationId(String featureName,String applicationId){
        return featureFlagRepository.findByFeatureNameAndApplicationId (featureName,applicationId);
    }

    public boolean isFeatureEnabledForUser(FeatureFlagEntity flag, String targetUserId){

        if(!flag.isEnabled ()){
            return false;
        }

        if(flag.getRolloutPercentage () >= 100){
            return true;
        }

        if(targetUserId == null || targetUserId.isEmpty ()){
            return false;
        }

        String hashString = targetUserId+"-"+flag.getFeatureName ();

        int hashValue = (hashString.hashCode () & 0x7fffffff) % 100;

        return hashValue < flag.getRolloutPercentage ();
    }
}
