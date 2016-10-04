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
    public List<WishesEntity> wishListByUserDesc(String username) {
        List<WishesEntity> list = wishesDao.findByOriginaluserOrderByVotecountDesc(username);
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

    //todo use vote method on "well" page
    @Override
    public WishesEntity addVote(String wish) {
        //get wish object
        WishesEntity wishesEntity = wishesDao.findByWish(wish);
        //add a vote
        wishesEntity.setVotecount(wishesEntity.getVotecount() + 1);
        //save back in db
        wishesDao.save(wishesEntity);
        return wishesEntity;
    }

    @Override
    public void deleteWish(WishesEntity deleted) {
        wishesDao.delete(deleted);
    }
}
