package com.webartifact.service;

import com.webartifact.dao.WishesDao;
import com.webartifact.model.WishesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishesServiceImpl implements WishesService {

    @Autowired
    private WishesDao wishesDao;

    @Override
    public List<WishesEntity> wishListByUser(String username) {
        List<WishesEntity> list = wishesDao.findByOriginaluser(username);
        return list;
    }

    @Override
    public List<WishesEntity> findAllWishes() {
        List<WishesEntity> list = wishesDao.findAll();
        return list;
    }

    @Override
    public WishesEntity saveWish(WishesEntity persisted) {
        WishesEntity wish = wishesDao.save(persisted);
        return wish;
    }

    @Override
    public void deleteWish(WishesEntity deleted) {
        wishesDao.delete(deleted);
    }
}
