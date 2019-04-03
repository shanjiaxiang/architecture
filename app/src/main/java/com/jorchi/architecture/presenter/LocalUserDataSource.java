package com.jorchi.architecture.presenter;

import com.jorchi.architecture.database.User;
import com.jorchi.architecture.database.UserDao;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalUserDataSource implements UserDataSourceInterface {
    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao mUserDao) {
        this.mUserDao = mUserDao;
    }

    @Override
    public Flowable<User> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public Completable insertOrUpdateUser(User user) {
        return mUserDao.insertUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
}
