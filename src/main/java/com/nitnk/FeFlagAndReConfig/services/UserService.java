package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void saveEntry(UserEntity userEntity) {
        userEntity.setPassword (passwordEncoder.encode (userEntity.getPassword ()));
        userEntity.setCreatedAt (LocalDateTime.now ());
        userEntity.setActive (true);
        userEntity.setRole ("ADMIN");
        userRepository.save (userEntity);
    }
    public boolean updateUser(UserEntity userEntity){
        UserEntity userExist = userRepository.findByUsername (userEntity.getUsername ());
        if(userExist != null){
            userExist.setUsername (userEntity.getUsername ());
            userExist.setPassword (userEntity.getPassword ());
            userExist.setActive (true);
            userExist.setCompanyName (userEntity.getCompanyName ());
            userExist.setEmail (userEntity.getEmail ());
            userRepository.save (userExist);
            return true;
        }
        return false;
    }

    public boolean checkCredential(UserEntity userEntity) {
        UserEntity user = userRepository.findByUsername (userEntity.getUsername ());
        if(user.getPassword ().equals (userEntity.getPassword ())){
            return true;
        }
        return false;
    }
    public UserEntity getUser(String username){
        return userRepository.findByUsername (username);
    }

    public boolean deleteUser(String username){
        UserEntity userEntity = userRepository.findByUsername (username);
        if(userEntity != null){
            userRepository.delete (userEntity);
            return true;
        }
        return false;
    }
}
