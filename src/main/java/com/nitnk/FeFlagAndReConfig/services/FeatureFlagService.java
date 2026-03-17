package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import com.nitnk.FeFlagAndReConfig.repository.FeatureFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    public boolean saveFeature(FeatureFlagEntity featureFlagEntity){
        if(featureFlagEntity != null){
            featureFlagRepository.save (featureFlagEntity);
            return true;
        }
        return false;
    }

    public FeatureFlagEntity findByFeatureName(String featureName){
        return featureFlagRepository.findByFeatureName (featureName);
    }
}
