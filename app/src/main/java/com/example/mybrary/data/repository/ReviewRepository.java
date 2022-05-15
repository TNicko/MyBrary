package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mybrary.data.firebase.dao.ReviewDAO;
import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.ReviewLocalDAO;
import com.example.mybrary.data.dataMapper.ReviewDataMapper;
import com.example.mybrary.data.local.entity.ReviewEntity;
import com.example.mybrary.domain.model.Review;

import java.util.HashMap;
import java.util.List;

public class ReviewRepository {

    private ReviewLocalDAO localDao;
    private ReviewDAO remoteDao;
    private ReviewDataMapper reviewMapper;
    private LiveData<List<Review>> readAllReviews;
    private MutableLiveData<List<Review>> review = new MutableLiveData<>();

    public ReviewRepository(Application application) {
        AppDatabase db;
        db = AppDatabase.getDbInstance(application);
        localDao = db.reviewDao();
        reviewMapper = new ReviewDataMapper();
        readAllReviews = reviewMapper.fromLiveEntityList(localDao.getAll());
    }

    // Return all reviews
    public LiveData<List<Review>> getAllReviews() {
        return readAllReviews;
    }

    // Get review by id
    public void getReviewById(String id) {
        String wordId = id.toString();
        QueryAsyncTask task = new QueryAsyncTask(localDao);
        task.delegate = this;
        task.execute(wordId);
    }

    // Return review
    public MutableLiveData<List<Review>> returnReview() {
        return review;
    }

    // Add review
    public void add(Review review) {
        // -> local db
        InsertAsyncTask task = new InsertAsyncTask(localDao);
        task.execute(review);

        // -> remote db
        remoteDao = new ReviewDAO();
        remoteDao.add(reviewMapper.mapToFirebase(review));
    }

    // Update review
    public void update(Review review) {
        // -> local db
        UpdateAsyncTask task = new UpdateAsyncTask(localDao);
        task.execute(review);

        // -> remote db
        remoteDao = new ReviewDAO();
        remoteDao.update(review.getWordId(), reviewMapper.mapToFirebase(review));
    }

    // Update review timer By ID
    public void updateTimerById(Boolean timer, String id) {
        // -> local db
        localDao.updateTimer(timer, id);

        // -> remote db
        HashMap<String, Object> items = new HashMap<>();
        items.put("timer", timer);
        remoteDao = new ReviewDAO();
        remoteDao.updateItem(id, items);
    }

    // Delete review
    public void delete(Review review) {
        // -> local db
        DeleteAsyncTask task = new DeleteAsyncTask(localDao);
        task.execute(review);

        // -> remote db
        remoteDao = new ReviewDAO();
        remoteDao.delete(review.getWordId());
    }

    // Add review locally on login
    public void addOnLogin(Review review) {
        InsertAsyncTask task = new InsertAsyncTask(localDao);
        task.execute(review);
    }

    // Delete all data
    public void nukeTable() {
        localDao.nukeTable();
    }

    private void asyncQueryFinished(List<Review> results) {
        review.setValue(results);
    }

    // Query By ID Data Async
    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Review>> {

        private ReviewLocalDAO asyncTaskDao;
        private ReviewRepository delegate = null;
        private ReviewDataMapper wordMapper = new ReviewDataMapper();

        QueryAsyncTask(ReviewLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Review> doInBackground(String... params) {
            return wordMapper.fromEntityList(asyncTaskDao.getById(params[0]));
        }

        @Override
        protected void onPostExecute(List<Review> result) {
            delegate.asyncQueryFinished(result);
        }
    }

    // Insert Data Async
    private static class InsertAsyncTask extends AsyncTask<Review, Void, Void> {

        private ReviewLocalDAO asyncTaskDao;
        private final ReviewDataMapper reviewMapper = new ReviewDataMapper();

        InsertAsyncTask(ReviewLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Review... params) {
            ReviewEntity reviewEntity = reviewMapper.mapToEntity(params[0]);
            asyncTaskDao.add(reviewEntity);
            return null;
        }
    }

    // Update Data Async
    private static class UpdateAsyncTask extends AsyncTask<Review, Void, Void> {

        private ReviewLocalDAO asyncTaskDao;
        private final ReviewDataMapper reviewMapper = new ReviewDataMapper();

        UpdateAsyncTask(ReviewLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Review... params) {
            ReviewEntity reviewEntity = reviewMapper.mapToEntity(params[0]);
            asyncTaskDao.update(reviewEntity);
            return null;
        }
    }

    // Delete Data Async
    private static class DeleteAsyncTask extends AsyncTask<Review, Void, Void> {

        private ReviewLocalDAO asyncTaskDao;
        private final ReviewDataMapper reviewMapper = new ReviewDataMapper();

        DeleteAsyncTask(ReviewLocalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Review... params) {
            ReviewEntity reviewEntity = reviewMapper.mapToEntity(params[0]);
            asyncTaskDao.delete(reviewEntity);
            return null;
        }
    }
}
