package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeatureFlagRepository extends MongoRepository<FeatureFlagEntity, String> {

    FeatureFlagEntity findByFeatureNameAndApplicationId(String featureName,String applicationId);
    List<FeatureFlagEntity> findByApplicationId(String applicationId);
    boolean deleteByFeatureNameAndApplicationId(String featureName, String applicationId);
}
