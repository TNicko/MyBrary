package com.example.mybrary.domain.model;

import java.io.Serializable;

public class Word implements Serializable {

    private String id;
    private String folder_id;
    private String word;
    private String translation;
    private String notes;
    private boolean review;

    public Word(String id, String folder_id, String word, String translation, String notes, boolean review) {
        this.id = id;
        this.folder_id = folder_id;
        this.word = word;
        this.translation = translation;
        this.notes = notes;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", folder_id=" + folder_id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", notes='" + notes + '\'' +
                ", review=" + review +
                '}';
    }
}
