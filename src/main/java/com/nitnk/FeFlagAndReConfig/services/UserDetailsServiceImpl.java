package com.nitnk.FeFlagAndReConfig.services;

import com.nitnk.FeFlagAndReConfig.entity.UserEntity;
import com.nitnk.FeFlagAndReConfig.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername (username);
        if(user != null){
            return User.builder ()
                    .username (user.getUsername ())
                    .password (user.getPassword ())
                    .build ();
        }
        throw new UsernameNotFoundException ("user not found with username "+username);
    }
}
