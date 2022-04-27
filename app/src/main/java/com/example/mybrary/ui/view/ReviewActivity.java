package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybrary.R;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.example.mybrary.domain.util.OnReviewCardTouch;
import com.example.mybrary.ui.viewmodel.ReviewViewModel;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private ReviewViewModel reviewViewModel;
    private List<Review> reviews;
    private List<Word> words;
    private MaterialCardView frontCard, backCard;
    private FrameLayout cardContainer, cardFrontParent, cardBackParent;
    private TextView reviewWord, reviewTranslation;
    private ProgressBar progressBar;
    private TextView progressBarLevel;
    private ConstraintLayout progressBarLayout;

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
        progressBar = findViewById(R.id.statusBar);
        progressBarLevel = findViewById(R.id.statusBarNo);
        progressBarLayout = findViewById(R.id.progressLayout);

        reviewWord.setText(words.get(0).getWord());
        reviewTranslation.setText(words.get(0).getTranslation());
        progressBar.setProgress((int) reviews.get(0).getLevel() * 5);

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
                // Word Review Completed
                if (dx > 450 || dx < -450) {
                    final long wordId = words.get(0).getId();
                    final long level = reviews.get(0).getLevel();
                    long newLevel = 0;

                    // Decrease word level
                    if (dx < -450){
                        System.out.println("Incorrect!");
                        cardContainer.setVisibility(View.GONE);
                        // if level already 0
                        if (level == 0) {
                            newLevel = 0;
                            Toast.makeText(ReviewActivity.this, "Retry in 30 seconds", Toast.LENGTH_SHORT).show();
                        } else {
                            newLevel = level - 1;
                        }
                    // Increase word level
                    } else if (dx > 450) {
                        System.out.println("Correct!");
                        cardContainer.setVisibility(View.GONE);
                        newLevel = level + 1;
                    }

                    // Animate progress bar
                    moveToCenter(progressBarLayout);
                    progressBar.setProgress((int) (newLevel * 5));
                    progressBarLevel.setText(String.valueOf(newLevel));

                }
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

    private void moveToCenter(View view) {
        ConstraintLayout root = findViewById(R.id.rootLayout);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        final int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        final int viewTop = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).bottomMargin + (view.getMeasuredHeight() / 2)+view.getBottom();
        final int viewBot = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).topMargin + (view.getMeasuredHeight() / 2)+view.getTop();

        System.out.println(viewBot - viewTop);

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        TranslateAnimation anim = new TranslateAnimation( 0,
                xDest - originalPos[0] ,
                (viewBot - viewTop),
                yDest - originalPos[1] );
        anim.setDuration(1000);
        anim.setFillAfter( true );
        view.startAnimation(anim);
    }

}