package com.example.mybrary.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.example.mybrary.data.local.AppDatabase;
import com.example.mybrary.data.local.dao.FolderLocalDAO;
import com.example.mybrary.data.local.dao.ReviewLocalDAO;
import com.example.mybrary.data.local.dao.WordLocalDAO;
import com.example.mybrary.data.local.dataMapper.FolderDataMapper;
import com.example.mybrary.data.local.dataMapper.ReviewDataMapper;
import com.example.mybrary.data.local.dataMapper.WordDataMapper;
import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.data.local.entity.ReviewEntity;
import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;

import java.util.List;

public class ReviewRepository {

    private ReviewLocalDAO localDao;
    private ReviewDataMapper reviewMapper;
    private LiveData<List<Review>> readAllReviews;

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

    // Add review
    public void add(Review review) {
        InsertAsyncTask task = new InsertAsyncTask(localDao);
        task.execute(review);
    }

    // Update review
    public void update(Review review) {
        UpdateAsyncTask task = new UpdateAsyncTask(localDao);
        task.execute(review);
    }

    // Delete review
    public void delete(Review review) {
        DeleteAsyncTask task = new DeleteAsyncTask(localDao);
        task.execute(review);
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
            System.out.println(params[0]);
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
            System.out.println(params[0]);
            ReviewEntity reviewEntity = reviewMapper.mapToEntity(params[0]);
            asyncTaskDao.update(reviewEntity);
            return null;
        }
    }

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
