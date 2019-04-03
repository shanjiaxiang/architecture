package com.jorchi.architecture.presenter;

import android.content.Context;

import com.jorchi.architecture.database.UsersDatabase;
import com.jorchi.architecture.ui.ViewModelFactory;


public class Injection {

    public static UserDataSourceInterface provideUserDataSource(Context context){
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        UserDataSourceInterface dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
