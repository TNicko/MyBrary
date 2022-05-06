package com.example.mybrary.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mybrary.data.firebase.FolderDAO;
import com.example.mybrary.data.firebase.ReviewDAO;
import com.example.mybrary.data.firebase.WordDAO;
import com.example.mybrary.data.repository.FolderRepository;
import com.example.mybrary.data.repository.ReviewRepository;
import com.example.mybrary.data.repository.WordRepository;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.model.Word;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginViewModel extends AndroidViewModel {

    FolderRepository folderRepo;
    WordRepository wordRepo;
    ReviewRepository reviewRepo;

    public LoginViewModel(Application application) {
        super(application);
        folderRepo = new FolderRepository(application);
        wordRepo = new WordRepository(application);
        reviewRepo = new ReviewRepository(application);
    }


    // Load All User Data from remote to local database on Login
    public void loadAll() {
        FolderDAO folderDao = new FolderDAO();
        WordDAO wordDao = new WordDAO();
        ReviewDAO reviewDao = new ReviewDAO();

        // Add all folders to local database
        folderDao.getAllFolders().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {

                    Map<String,Object> td = (HashMap<String, Object>)data.getValue();
                    Folder folder = new Folder(td.get("id").toString(), td.get("name").toString());
                    folderRepo.addOnLogin(folder);
                    System.out.println("Added folder: "+ folder);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add all words to local database
        wordDao.getWords().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Map<String, Object> td = (HashMap<String, Object>) data.getValue();
                    boolean review = (boolean) td.get("review");
                    Word word = new Word(td.get("id").toString(),
                            td.get("folder_id").toString(),
                            td.get("word").toString(),
                            td.get("translation").toString(),
                            td.get("notes").toString(),
                            review);
                    wordRepo.addOnLogin(word);
                    System.out.println("Added word: "+word);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add all reviews to local database
        reviewDao.getAllReviews().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Map<String, Object> td = (HashMap<String, Object>) data.getValue();
                    long level = Long.parseLong(td.get("level").toString());
                    Date dateCreated = (Date) td.get("dateCreated");
                    boolean timer = (boolean) td.get("timer");
                    Review review = new Review(
                            td.get("wordId").toString(),
                            level,
                            dateCreated,
                            timer
                    );
                    reviewRepo.addOnLogin(review);
                    System.out.println("Added review: "+review);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
