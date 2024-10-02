package com.aplikasi.binarfudv2.repo.oauth;

import com.aplikasi.binarfudv2.entity.oauth.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface RoleRepo extends PagingAndSortingRepository<Role, Long> {
    Role findOneByName(String name);

    List<Role> findByNameIn(String[] names);
}