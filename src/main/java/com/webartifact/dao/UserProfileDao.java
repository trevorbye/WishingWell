package com.webartifact.dao;

import com.webartifact.model.UserProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by trevorBye on 9/21/16.
 */

@Repository
public interface UserProfileDao extends CrudRepository<UserProfileEntity, Long> {

    UserProfileEntity findByUsername(String username);

    List<UserProfileEntity> findAll();

    @SuppressWarnings("unchecked")
    UserProfileEntity save(UserProfileEntity persisted);
}
