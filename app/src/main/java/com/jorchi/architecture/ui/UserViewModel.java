package com.jorchi.architecture.ui;


import com.jorchi.architecture.database.User;
import com.jorchi.architecture.presenter.UserDataSourceInterface;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class UserViewModel extends ViewModel {
    private final UserDataSourceInterface mDataSource;
    private User mUser;


    public UserViewModel(UserDataSourceInterface dataSource) {
        this.mDataSource = dataSource;
    }

    public Flowable<String> getUserName(){
        return mDataSource.getUser().map(user -> {
           mUser = user;
           return user.getUserName();
        });
    }

    public Completable updateUserName(final String userName){
        mUser = mUser == null? new User(userName): new User(mUser.getId(), userName);
        return mDataSource.insertOrUpdateUser(mUser);
    }

}
