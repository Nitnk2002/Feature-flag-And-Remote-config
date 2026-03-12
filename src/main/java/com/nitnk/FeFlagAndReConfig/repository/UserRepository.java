package com.nitnk.FeFlagAndReConfig.repository;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}
