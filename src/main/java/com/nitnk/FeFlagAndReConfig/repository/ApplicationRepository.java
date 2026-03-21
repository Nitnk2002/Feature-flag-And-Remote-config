package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<ApplicationEntity, String> {

    ApplicationEntity findByUserId(String userId);

    ApplicationEntity findByApiKey(String apiKey);
}
