package com.webartifact.model;

import javax.persistence.*;


@Entity
@Table(name = "VoteByProfile", schema = "dbo", catalog = "DevTestTeam")
public class VoteByUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "wish", nullable = false, length = 20)
    private String wish;

    @Column(name = "voter", nullable = false, length = 20)
    private String voter;

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

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public VoteByUser(String wish, String voter) {
        this.wish = wish;
        this.voter = voter;
    }

    public VoteByUser() {
    }
}
