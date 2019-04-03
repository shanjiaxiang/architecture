package com.jorchi.architecture.presenter;

import com.jorchi.architecture.database.User;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface UserDataSourceInterface {

    Flowable<User> getUser();

    Completable insertOrUpdateUser(User user);

    void deleteAllUsers();
}
