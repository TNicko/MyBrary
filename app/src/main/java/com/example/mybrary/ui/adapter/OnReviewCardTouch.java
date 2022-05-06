package com.example.mybrary.ui.adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnReviewCardTouch implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private boolean isScrolling;
    private float dx;
    int[] location;

    public OnReviewCardTouch(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeRight() {

    }

    public void onSwipeLeft() {

    }

    public void flipView() {

    }

    public void onMoveRight(float dX, float totaldx) {

    }

    public void onMoveLeft(float dX, float totaldx) {

    }

    public void onRelease(float newX, float dx) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // When scrolling event is finished
            if(isScrolling) {
                location = new int[2];
                view.getLocationOnScreen(location);
                onRelease(location[0], dx);
                dx = 0;
                isScrolling = false;
            }
        }

        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 10;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            flipView();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            isScrolling = true;

            float dX = e2.getX() - e1.getX();
            float dY = e2.getY() - e1.getY();

            if (dX > 0) {
                onMoveRight(dX, dx);
            }
            else if (dX < 0) {
                onMoveLeft(dX, dx);
            }

            dx += dX;
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY)
            && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD
            && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                return true;
            }
            return false;
        }


    }
}
