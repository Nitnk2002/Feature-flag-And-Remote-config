package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.dto.request.CreateFeatureRequest;
import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    @Autowired
    private FeatureFlagRepository featureFlagRepository;
    @Autowired
    private RedisService redisService;

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

    public boolean updateFeature(CreateFeatureRequest featureRequest,String applicationId,String featureName){
        if(featureRequest == null){
            return false;
        }
        FeatureFlagEntity flagEntity = featureFlagRepository.findByFeatureNameAndApplicationId (
                featureName,
                applicationId);

        if(flagEntity == null){
            return false;
        }

        flagEntity.setFeatureName (featureRequest.getFeatureName ());
        flagEntity.setEnabled (featureRequest.isEnabled ());
        flagEntity.setRolloutPercentage (featureRequest.getRolloutPercentage ());
        featureFlagRepository.save (flagEntity);

        String redisKey = featureName+":" + applicationId;
        redisService.delete(redisKey);
        return true;

    }
    public FeatureFlagEntity findByFeatureNameAndApplicationId(String featureName,String applicationId){
        FeatureFlagEntity redisFlag = redisService.get (featureName+":"+applicationId,FeatureFlagEntity.class);
        if(redisFlag != null){
            return redisFlag;
        }
        FeatureFlagEntity dbFlag = featureFlagRepository.findByFeatureNameAndApplicationId (featureName,applicationId);
        if(dbFlag != null) {
            redisService.set (featureName + ":" + applicationId, dbFlag, 86400L);
        }
        return dbFlag;
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

    public boolean deleteFeature(String featureName, String applicationId) {

        boolean isDeleted = featureFlagRepository.deleteByFeatureNameAndApplicationId(featureName,applicationId);
        String key = featureName+":"+applicationId;
        if(isDeleted){
            redisService.delete (key);
            return true;
        }
        return false;
    }
}
