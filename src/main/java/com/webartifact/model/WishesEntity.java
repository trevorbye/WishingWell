package com.webartifact.model;

import javax.persistence.*;

/**
 * Created by trevorBye on 9/19/16.
 */
@Entity
@Table(name = "Wishes", schema = "dbo", catalog = "DevTestTeam")
public class WishesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "wish", nullable = true, length = 20)
    private String wish;

    @Column(name = "votecount", nullable = true)
    private long votecount;

    @Column(name = "originaluser", nullable = true, length = 20)
    private String originaluser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public long getVotecount() {
        return votecount;
    }

    public void setVotecount(long votecount) {
        this.votecount = votecount;
    }

    public String getOriginaluser() {
        return originaluser;
    }

    public void setOriginaluser(String originaluser) {
        this.originaluser = originaluser;
    }


}
