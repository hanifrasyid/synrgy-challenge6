package com.aplikasi.binarfudv2.service.oauth;

import com.aplikasi.binarfudv2.entity.oauth.User;
import com.aplikasi.binarfudv2.repo.oauth.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findOneByUsername(s);
        if (null == user) {
            throw new UsernameNotFoundException(String.format("Username %s is not found", s));
        }

        return user;
    }

    @CacheEvict("oauth_username")
    public void clearCache(String s) {
    }
}