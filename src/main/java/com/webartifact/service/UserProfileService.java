package com.webartifact.service;

import com.webartifact.model.UserProfileEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by trevorBye on 9/21/16.
 */
public interface UserProfileService extends UserDetailsService {
    UserProfileEntity findByUser(String username);
    List<UserProfileEntity> findAll();
    UserProfileEntity save(UserProfileEntity persisted);
}
