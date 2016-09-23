package com.webartifact.dao;

import com.webartifact.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by trevorBye on 9/22/16.
 */

@Repository
public interface RoleDao extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
