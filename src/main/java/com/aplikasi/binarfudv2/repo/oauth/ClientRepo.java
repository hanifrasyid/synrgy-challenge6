package com.aplikasi.binarfudv2.repo.oauth;

import com.aplikasi.binarfudv2.entity.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepo extends PagingAndSortingRepository<Client, Long> {
    Client findOneByClientId(String clientId);
}