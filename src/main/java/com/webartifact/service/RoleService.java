package com.webartifact.service;

import com.webartifact.model.RoleEntity;

/**
 * Created by trevorBye on 9/22/16.
 */
public interface RoleService {
    RoleEntity findByName(String name);
}
