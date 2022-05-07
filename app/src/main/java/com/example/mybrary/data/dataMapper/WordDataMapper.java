package com.example.mybrary.data.dataMapper;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.mybrary.data.local.entity.WordEntity;
import com.example.mybrary.domain.model.Word;

import java.util.List;
import java.util.stream.Collectors;

public class WordDataMapper {

    public Word mapFromEntity(WordEntity entity) {
        return new Word(entity.id, entity.folder_id, entity.word, entity.translation, entity.notes, entity.review);
    }

    public WordEntity mapToEntity(Word domainModel) {
        return new WordEntity(domainModel.getId(), domainModel.getFolder_id(), domainModel.getWord(), domainModel.getTranslation(), domainModel.getNotes(), domainModel.isReview());
    }

    public List<Word> fromEntityList(List<WordEntity> initial) {
        return initial.stream().map(this::mapFromEntity).collect(Collectors.toList());
    }

    public LiveData<List<Word>> fromLiveEntityList(LiveData<List<WordEntity>> initial) {
        return Transformations.map(initial, new Function<List<WordEntity>, List<Word>>() {
            @Override
            public List<Word> apply(List<WordEntity> input) {
                return fromEntityList(input);
            }
        });
    }

    public List<WordEntity> toEntityList(List<Word> initial) {
        return initial.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
