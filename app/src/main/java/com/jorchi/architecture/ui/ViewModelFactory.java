package com.jorchi.architecture.ui;

import com.jorchi.architecture.presenter.UserDataSourceInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserDataSourceInterface mDataSource;

    public ViewModelFactory(UserDataSourceInterface mDataSource) {
        this.mDataSource = mDataSource;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
