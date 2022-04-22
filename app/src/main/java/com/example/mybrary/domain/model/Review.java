package com.example.mybrary.domain.model;

import java.util.Date;

public class Review {

    Long wordId;
    Long level;
    Date dateCreated;

    public Review(Long wordId, Long level, Date dateCreated) {
        this.wordId = wordId;
        this.level = level;
        this.dateCreated = dateCreated;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Review{" +
                "wordId=" + wordId +
                ", level=" + level +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
