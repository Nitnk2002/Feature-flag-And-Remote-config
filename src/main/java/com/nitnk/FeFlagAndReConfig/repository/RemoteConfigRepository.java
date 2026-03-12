package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.RemoteConfigEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RemoteConfigRepository extends MongoRepository<RemoteConfigEntity, String> {

    RemoteConfigEntity findByKey(String key);
}
