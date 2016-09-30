package com.webartifact.service;

import com.webartifact.dao.VoteDao;
import com.webartifact.model.VoteByUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by trevorBye on 9/25/16.
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteDao voteDao;


    @Override
    public VoteByUser findByWishAndVoter(String wish, String voter) {
        VoteByUser voteByUser = voteDao.findByWishAndVoter(wish,voter);
        return voteByUser;
    }

    @Override
    public VoteByUser saveVote(VoteByUser voteByUser) {
        VoteByUser vote = voteDao.save(voteByUser);
        return vote;
    }
}
