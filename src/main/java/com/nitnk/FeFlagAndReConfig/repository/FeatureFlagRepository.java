package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.FeatureFlagEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureFlagRepository extends MongoRepository<FeatureFlagEntity, String> {

    FeatureFlagEntity findByFeatureName(String featureName);
}
