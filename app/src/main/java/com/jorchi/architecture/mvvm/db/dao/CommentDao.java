package com.jorchi.architecture.mvvm.db.dao;


import com.jorchi.architecture.mvvm.db.entity.CommentEntity;
import com.jorchi.architecture.mvvm.model.Comment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comments WHERE productId = :productId")
    LiveData<List<CommentEntity>> loadComments(int productId);

    @Query("SELECT * FROM comments WHERE productId = :productId")
    List<CommentEntity> loadCommentsSync(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CommentEntity> comments);
}
