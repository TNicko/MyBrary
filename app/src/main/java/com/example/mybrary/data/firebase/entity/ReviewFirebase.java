package com.example.mybrary.data.firebase.entity;

import java.sql.Timestamp;
import java.util.Date;

// Review Model for Firebase
public class ReviewFirebase {

    String wordId;
    long level;
    long dateCreated;
    Boolean timer;

    public ReviewFirebase(String wordId, long level, long dateCreated, Boolean timer) {
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

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
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
        return "ReviewFirebase{" +
                "wordId='" + wordId + '\'' +
                ", level=" + level +
                ", dateCreated=" + dateCreated +
                ", timer=" + timer +
                '}';
    }
}
