package com.example.mybrary.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
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
import android.view.animation.ScaleAnimation;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private FloatingActionButton nextButton;

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
        nextButton = findViewById(R.id.nextReviewBtn);

        reviewWord.setText(words.get(0).getWord());
        reviewTranslation.setText(words.get(0).getTranslation());
        progressBar.setProgress((int) reviews.get(0).getLevel() * 50);
        progressBarLevel.setText(String.valueOf(reviews.get(0).getLevel()));

        // Modify camera scale for flip animation
        int camDistance = 20000;
        Float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        cardFrontParent.setCameraDistance(camDistance * scale);
        cardBackParent.setCameraDistance(camDistance * scale);

        // Set Flip Animation
        frontFlipAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_flip);
        backFlipAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_flip);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove completed review from list
                reviews.remove(0);
                words.remove(0);

                if (reviews.size() == 0) {
                    mainActivity();
                } else {
                    newReviewCard();

                }
            }
        });


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
                        cardContainer.setVisibility(View.INVISIBLE);
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
                        cardContainer.setVisibility(View.INVISIBLE);
                        newLevel = level + 1;
                    }

                    // Animate progress bar
                    animateProgressBar(progressBarLayout, newLevel, false);

                    reviewViewModel.updateReview(wordId, reviews.get(0).getDateCreated(), newLevel);
                    reviewViewModel.setReviewCountdown(wordId, newLevel);
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

    // Translate Animation to move view to center of screen
    private void animateProgressBar(View view, long newLevel, boolean isReset) {

        List<Integer> translateInts = locateProgressBar(view);
        int fromYDelta = translateInts.get(0);
        int toYDelta = translateInts.get(1);
        int toXDelta = translateInts.get(2);

        // Move Progress bar to center
        TranslateAnimation anim = new TranslateAnimation( 0,
                toXDelta ,
                0,
                toYDelta );
        anim.setDuration(1000);
        anim.setFillAfter( true );
        view.startAnimation(anim);

        // Scale Progress bar
        view.animate().scaleX(2f).scaleY(2f).setDuration(1000);

        // change progress on progress bar
        ValueAnimator animator = ValueAnimator.ofInt((int) (newLevel * 50));
        animator.setDuration(1200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressBar.setProgress((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }, 500);
        progressBarLevel.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarLevel.setText(String.valueOf(newLevel));
                nextButton.setVisibility(View.VISIBLE);
            }
        }, 1200);
    }

    // Reverse progress bar animation
    private void resetProgressBar(View view, long newLevel) {

        List<Integer> translateInts = locateProgressBar(view);
        int fromYDelta = translateInts.get(0);
        int toYDelta = translateInts.get(1);
        int toXDelta = translateInts.get(2);

        final int viewTop = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).bottomMargin + (view.getMeasuredHeight() / 2)+view.getBottom();
        final int cardBot = ((ConstraintLayout.LayoutParams) cardContainer.getLayoutParams()).topMargin + (cardContainer.getMeasuredHeight() / 2)+cardContainer.getTop();
        final int viewBot = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).topMargin + (view.getMeasuredHeight() / 2)+view.getTop();

        int margin = (viewTop - viewBot) / 2;

        // Move Progress bar to center
        TranslateAnimation anim = new TranslateAnimation( 0,
                0,
                toYDelta - margin,
                0);
        anim.setDuration(1000);
        anim.setFillAfter( true );
        view.startAnimation(anim);

        // Scale Progress bar
        view.animate().scaleX(1f).scaleY(1f).setDuration(1000);

        // change progress on progress bar
        ValueAnimator animator = ValueAnimator.ofInt((int) (newLevel * 50));
        animator.setDuration(1200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressBar.setProgress((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }, 500);
        progressBarLevel.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarLevel.setText(String.valueOf(newLevel));
            }
        }, 1000);

    }

    // Get location values of progress bar
    private List<Integer> locateProgressBar(View view) {
        ConstraintLayout root = findViewById(R.id.rootLayout);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        final int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        final int viewTop = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).bottomMargin + (view.getMeasuredHeight() / 2)+view.getBottom();
        final int viewBot = ((ConstraintLayout.LayoutParams) view.getLayoutParams()).topMargin + (view.getMeasuredHeight() / 2)+view.getTop();

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        int fromYDelta = (viewBot - viewTop)/2;
        int toYDelta = yDest - (originalPos[1]*2); // "*2" slightly higher than center
        int toXDelta = xDest  - originalPos[0];
//        int fromYDelta = viewTop;

        System.out.println("viewTop: "+viewTop);
        System.out.println("viewBot: "+viewBot);
        System.out.println("xDest: "+xDest);
        System.out.println("location X: "+originalPos[0]);
        System.out.println("yDest: "+ yDest);
        System.out.println("fromYDelta: "+fromYDelta);
        System.out.println("toYDelta: "+toYDelta);
        System.out.println("toXDelta: "+toXDelta);

        List<Integer> translateInts = new ArrayList<>();
        translateInts.add(fromYDelta);
        translateInts.add(toYDelta);
        translateInts.add(toXDelta);

        return translateInts;
    }

    public void newReviewCard() {

        // set new review info
        long newLevel = reviews.get(0).getLevel();
        resetProgressBar(progressBarLayout, newLevel);
        reviewWord.setText(words.get(0).getWord());
        reviewTranslation.setText(words.get(0).getTranslation());
        nextButton.setVisibility(View.GONE);
        cardContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                cardContainer.setVisibility(View.VISIBLE);

            }
        }, 1000);

    }

    // Switch Activity -> MainActivity
    public void mainActivity() {
        Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
        this.startActivity(intent);
    }

}