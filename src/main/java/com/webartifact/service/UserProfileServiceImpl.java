package com.webartifact.service;

import com.webartifact.dao.UserProfileDao;
import com.webartifact.model.UserProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService{
    @Autowired
    private UserProfileDao userDao;

    @Override
    public UserProfileEntity findByUser(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<UserProfileEntity> findAll() {
        List<UserProfileEntity> list = userDao.findAll();
        return list;
    }

    @Override
    public UserProfileEntity save(UserProfileEntity persisted) {
        userDao.save(persisted);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfileEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return user;
    }
}
