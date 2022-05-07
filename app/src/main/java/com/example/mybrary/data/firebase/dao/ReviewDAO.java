package com.example.mybrary.data.firebase.dao;

import com.example.mybrary.data.firebase.entity.ReviewFirebase;
import com.example.mybrary.domain.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class ReviewDAO {

    private final DatabaseReference dbReference;

    public ReviewDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbReference = db.getReference().child("reviews").child(userId);
    }

    // Get all reviews
    public Query getAllReviews() {
        return dbReference.orderByKey();
    }

    // Add review
    public void add(ReviewFirebase review) {
        dbReference.child(review.getWordId()).setValue(review);
    }

    // Delete review
    public void delete(String key) {
        dbReference.child(key).removeValue();
    }

    // Update review
    public void update(String key, ReviewFirebase review) {
        dbReference.child(key).setValue(review);
    }

    // Update item(s) in review
    public void updateItem(String key, HashMap<String, Object> hashMap) {
        dbReference.child(key).updateChildren(hashMap);
    }
}
