package com.webartifact.service;

import com.webartifact.model.WishesEntity;

import java.util.List;

/**
 * Created by trevorBye on 9/21/16.
 */
public interface WishesService {
    List<WishesEntity> wishListByUser(String username);
    List<WishesEntity> findAllWishes();
    WishesEntity saveWish(WishesEntity persisted);
    WishesEntity addVote(String wish);
    void deleteWish(WishesEntity deleted);
}
