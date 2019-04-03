package com.jorchi.architecture.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {
    private static volatile UsersDatabase INSTANCE;
    public abstract UserDao userDao();


    public static UsersDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (UsersDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UsersDatabase.class, "Sample.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
