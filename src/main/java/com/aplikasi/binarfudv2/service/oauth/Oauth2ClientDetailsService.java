package com.aplikasi.binarfudv2.service.oauth;

import com.aplikasi.binarfudv2.repo.oauth.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        ClientDetails client = clientRepo.findOneByClientId(s);
        if (null == client) {
            throw new ClientRegistrationException("Client not found");
        }

        return client;
    }

    @CacheEvict("oauth_client_id")
    public void clearCache(String s) {
        System.out.println("this is cache of oauth_client_id = " + s);
    }
}