package com.webartifact.dao;

import com.webartifact.model.VoteByUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDao extends CrudRepository<VoteByUser,Long> {
    VoteByUser findByWishAndVoter(String wish, String voter);

    @SuppressWarnings("unchecked")
    VoteByUser save(VoteByUser persisted);
}
