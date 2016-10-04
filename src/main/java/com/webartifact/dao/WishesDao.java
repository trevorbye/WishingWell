package com.webartifact.dao;

import com.webartifact.model.WishesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by trevorBye on 9/21/16.
 */

@Repository
public interface WishesDao extends CrudRepository<WishesEntity, Long>{
    //returns list of wish objects for a unique user (use in trophy case)
    List<WishesEntity> findByOriginaluserOrderByVotecountDesc(String username);

    //query all wishes
    List<WishesEntity> findAll();

    //save wish
    @SuppressWarnings("unchecked")
    WishesEntity save(WishesEntity persisted);

    //delete wish
    void delete(WishesEntity deleted);

    //find wish by name
    WishesEntity findByWish(String wish);

}
