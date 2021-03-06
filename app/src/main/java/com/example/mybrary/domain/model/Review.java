package com.example.mybrary.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {

    String wordId;
    long level;
    Date dateCreated;
    Boolean timer;

    public Review(String wordId, long level, Date dateCreated, Boolean timer) {
        this.wordId = wordId;
        this.level = level;
        this.dateCreated = dateCreated;
        this.timer = timer;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
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

    public Boolean getTimer() {
        return timer;
    }

    public void setTimer(Boolean timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "wordId=" + wordId +
                ", level=" + level +
                ", dateCreated=" + dateCreated +
                ", timer=" + timer +
                '}';
    }
}
