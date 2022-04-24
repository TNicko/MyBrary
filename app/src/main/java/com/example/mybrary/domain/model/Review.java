package com.example.mybrary.domain.model;

import java.util.Date;

public class Review {

    long wordId;
    long level;
    Date dateCreated;

    public Review(long wordId, long level, Date dateCreated) {
        this.wordId = wordId;
        this.level = level;
        this.dateCreated = dateCreated;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
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
