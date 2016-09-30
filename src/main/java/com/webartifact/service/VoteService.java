package com.webartifact.service;

import com.webartifact.model.VoteByUser;

/**
 * Created by trevorBye on 9/25/16.
 */
public interface VoteService {
    VoteByUser findByWishAndVoter(String wish, String voter);
    VoteByUser saveVote(VoteByUser voteByUser);
}
