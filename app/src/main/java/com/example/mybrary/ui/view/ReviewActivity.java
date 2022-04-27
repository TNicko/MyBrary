package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.domain.util.OnReviewCardTouch;
import com.example.mybrary.ui.viewmodel.ReviewViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private ReviewViewModel reviewViewModel;
    private List<Review> reviews;
    private List<Word> words;
    private MaterialCardView frontCard, backCard;
    private FrameLayout cardContainer, cardFrontParent, cardBackParent;
    private TextView reviewWord, reviewTranslation;

    // Animation variables
    AnimatorSet frontFlipAnim, backFlipAnim;
    private Boolean isFront = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

//        if (savedInstanceState == null) {
//            this.overridePendingTransition(R.anim.slide_out_right,
//                    R.anim.slide_out_left);
//        }

        Intent intent = getIntent();
        Bundle bundleReview = intent.getBundleExtra("BUNDLE_REVIEW");
        Bundle bundleWord = intent.getBundleExtra("BUNDLE_WORD");
        reviews = (List<Review>) bundleReview.getSerializable("REVIEW_LIST");
        words = (ArrayList<Word>) bundleWord.getSerializable("WORD_LIST");

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        // Get Views
        cardContainer = findViewById(R.id.card_container);
        cardFrontParent = findViewById(R.id.cardFrontParent);
        cardBackParent = findViewById(R.id.cardBackParent);
        frontCard = findViewById(R.id.cardFront);
        backCard = findViewById(R.id.cardBack);
        reviewWord = findViewById(R.id.review_word);
        reviewTranslation = findViewById(R.id.review_translation);


//        reviewWord.setText(words.get(0).getWord());
//        reviewTranslation.setText(words.get(0).getTranslation());

        // Modify camera scale for flip animation
        int camDistance = 20000;
        Float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        cardFrontParent.setCameraDistance(camDistance * scale);
        cardBackParent.setCameraDistance(camDistance * scale);

        // Set Flip Animation
        frontFlipAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_flip);
        backFlipAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_flip);


        // Review Card Touch Events
        cardContainer.setOnTouchListener(new OnReviewCardTouch(getApplicationContext()) {

            @Override
            public void onRelease(float newX, float dx) {
                cardContainer.setX(newX - dx);
                frontCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
                backCard.setCardBackgroundColor(getResources().getColor(R.color.purple_200));

            }

            @Override
            public void onSwipeLeft() {
                System.out.println("SWIPED LEFT");
                Toast.makeText(ReviewActivity.this, "left swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void flipView() {
                if (isFront) {
                    frontFlipAnim.setTarget(cardFrontParent);
                    backFlipAnim.setTarget(cardBackParent);
                    frontFlipAnim.start();
                    backFlipAnim.start();
                    isFront = false;
                } else {
                    frontFlipAnim.setTarget(cardBackParent);
                    backFlipAnim.setTarget(cardFrontParent);
                    backFlipAnim.start();
                    frontFlipAnim.start();
                    isFront = true;
                }
            }

            @Override
            public void onMoveLeft(float dX, float totaldx) {
                cardContainer.setX(cardContainer.getX() + dX);
                if (totaldx < -450 || totaldx > 450) {
                    frontCard.setCardBackgroundColor(getResources().getColor(R.color.reviewCardOpaque));
                    backCard.setCardBackgroundColor(getResources().getColor(R.color.reviewCardOpaque));


                } else {
                    frontCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
                    backCard.setCardBackgroundColor(getResources().getColor(R.color.purple_200));
                }
            }

            @Override
            public void onMoveRight(float dX, float totaldx) {
                cardContainer.setX(cardContainer.getX() + dX);
                if (totaldx < -450 || totaldx > 450) {
                    frontCard.setCardBackgroundColor(getResources().getColor(R.color.reviewCardOpaque));
                    backCard.setCardBackgroundColor(getResources().getColor(R.color.reviewCardOpaque));


                } else {
                    frontCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
                    backCard.setCardBackgroundColor(getResources().getColor(R.color.purple_200));

                }
            }
        });
    }

}