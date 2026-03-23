package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.dto.request.UserSignupRequest;
import com.nitnk.FeFlagAndReConfig.dto.response.UserProfileResponse;
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

    public void saveEntry(UserSignupRequest user) {
        UserEntity userEntity = new UserEntity ();
        userEntity.setUsername (user.getUsername ());
        userEntity.setEmail (user.getEmail ());
        userEntity.setPassword (passwordEncoder.encode (userEntity.getPassword ()));
        userEntity.setCreatedAt (LocalDateTime.now ());
        userEntity.setActive (true);
        userEntity.setRole ("ADMIN");
        userRepository.save (userEntity);
    }
    public boolean updateUser(UserSignupRequest user){
        UserEntity userExist = userRepository.findByUsername (user.getUsername ());
        if(userExist != null){
            userExist.setUsername (user.getUsername ());
            userExist.setPassword (passwordEncoder.encode (user.getPassword ()));
            userExist.setCompanyName (user.getCompanyName ());
            userExist.setEmail (user.getEmail ());
            userRepository.save (userExist);
            return true;
        }
        return false;
    }

    public UserProfileResponse getUser(String username){
        UserEntity userEntity = userRepository.findByUsername (username);
        UserProfileResponse user = new UserProfileResponse ();
        user.setUsername (userEntity.getUsername ());
        user.setEmail (userEntity.getEmail ());
        user.setCompanyName (userEntity.getCompanyName ());
        user.setRole (userEntity.getRole ());
        return user;
    }

    public String getUserId(String username){
        UserEntity userEntity = userRepository.findByUsername (username);
        return userEntity.getId ();
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
