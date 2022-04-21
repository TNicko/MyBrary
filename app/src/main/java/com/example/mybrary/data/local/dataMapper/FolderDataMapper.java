package com.example.mybrary.data.local.dataMapper;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.mybrary.data.local.entity.FolderEntity;
import com.example.mybrary.domain.model.Folder;
import com.example.mybrary.domain.util.EntityMapper;

import java.util.List;
import java.util.stream.Collectors;

public class FolderDataMapper implements EntityMapper<FolderEntity, Folder> {
    @Override
    public Folder mapFromEntity(FolderEntity entity) {
        return new Folder(entity.id, entity.name);
    }

    @Override
    public FolderEntity mapToEntity(Folder domainModel) {
        return new FolderEntity(domainModel.getId(), domainModel.getName());
    }

    public List<Folder> fromEntityList(List<FolderEntity> initial) {
        return initial.stream().map(this::mapFromEntity).collect(Collectors.toList());
    }

    public LiveData<List<Folder>> fromLiveEntityList(LiveData<List<FolderEntity>> initial) {
        return Transformations.map(initial, new Function<List<FolderEntity>, List<Folder>>() {
            @Override
            public List<Folder> apply(List<FolderEntity> input) {
                return fromEntityList(input);
            }
        });
    }

    public List<FolderEntity> toEntityList(List<Folder> initial) {
        return initial.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
