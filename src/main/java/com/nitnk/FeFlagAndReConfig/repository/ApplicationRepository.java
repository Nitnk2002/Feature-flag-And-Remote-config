package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<ApplicationEntity, String> {

    List<ApplicationEntity> findByUserId(String userId);
    ApplicationEntity findByUserIdAndAppName(String userId,String appName);
    ApplicationEntity findByApiKey(String apiKey);
}
