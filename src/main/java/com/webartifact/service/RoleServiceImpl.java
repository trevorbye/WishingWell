package com.webartifact.service;

import com.webartifact.dao.RoleDao;
import com.webartifact.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by trevorBye on 9/22/16.
 */

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDao roleDao;

    @Override
    public RoleEntity findByName(String name) {
        RoleEntity roleEntity = roleDao.findByName(name);
        return roleEntity;
    }
}
