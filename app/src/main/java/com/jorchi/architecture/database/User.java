package com.jorchi.architecture.database;


import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userid")
    private String mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @Ignore
    public User(String userName){
        mId = UUID.randomUUID().toString();
        mUserName = userName;
    }

    public User(String id, String userName){
        this.mId = id;
        this.mUserName = userName;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String mId) {
        this.mId = mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}


