package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(UserEntity userEntity) {
        userEntity.setCreatedAt (LocalDateTime.now ());
        userEntity.setActive (true);
        userEntity.setRole ("ADMIN");
        userRepository.save (userEntity);
    }

    public boolean checkCredential(UserEntity userEntity) {
        UserEntity user = userRepository.findByUsername (userEntity.getUsername ());
        if(user.getPassword ().equals (userEntity.getPassword ())){
            return true;
        }
        return false;
    }
}
