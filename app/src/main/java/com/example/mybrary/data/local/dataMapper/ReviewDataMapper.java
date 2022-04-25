package com.example.mybrary.data.local.dataMapper;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.mybrary.data.local.entity.ReviewEntity;
import com.example.mybrary.domain.model.Review;
import com.example.mybrary.domain.util.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewDataMapper implements EntityMapper<ReviewEntity, Review> {

    @Override
    public Review mapFromEntity(ReviewEntity entity) {
        return new Review(entity.wordId, entity.level, entity.date_created, entity.timer);
    }

    @Override
    public ReviewEntity mapToEntity(Review domainModel) {
        return new ReviewEntity(domainModel.getWordId(), domainModel.getLevel(), domainModel.getDateCreated(), domainModel.getTimer());
    }

    public List<Review> fromEntityList(List<ReviewEntity> initial) {
        return initial.stream().map(this::mapFromEntity).collect(Collectors.toList());
    }

    public LiveData<List<Review>> fromLiveEntityList(LiveData<List<ReviewEntity>> initial) {
        return Transformations.map(initial, new Function<List<ReviewEntity>, List<Review>>() {
            @Override
            public List<Review> apply(List<ReviewEntity> input) {
                return fromEntityList(input);
            }
        });
    }

    public List<ReviewEntity> toEntityList(List<Review> initial) {
        return initial.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
