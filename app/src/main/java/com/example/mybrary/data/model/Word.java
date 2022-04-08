package com.example.mybrary.data.model;

public class Word {

    private String word;
    private String translation;
    private String notes;
    private String id;

    public Word(String word, String translation, String notes, String id) {
        this.word = word;
        this.translation = translation;
        this.notes = notes;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", notes='" + notes + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
