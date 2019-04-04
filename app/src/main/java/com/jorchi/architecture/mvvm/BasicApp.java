package com.jorchi.architecture.mvvm;

import android.app.Application;

import com.jorchi.architecture.mvvm.db.AppDatabase;

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository(){
        return DataRepository.getInstance(getDatabase());
    }
}
