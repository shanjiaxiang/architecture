package com.jorchi.architecture;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jorchi.architecture.presenter.Injection;
import com.jorchi.architecture.ui.UserViewModel;
import com.jorchi.architecture.ui.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView mName;
    EditText mInput;
    Button mUpdate;

    private final String TAG = "Main";
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(com.jorchi.architecture.ui.UserViewModel.class);

        mName = findViewById(R.id.user_name);
        mInput = findViewById(R.id.user_name_input);
        mUpdate = findViewById(R.id.update_user);
        mUpdate.setOnClickListener(v -> {
            updateUserName();
        });


        mDisposable.add(mViewModel.getUserName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userName-> mName.setText(userName),
                        throwable -> Log.e(TAG, "Unable to update username", throwable)));
    }

    private void updateUserName(){
        String userName = mInput.getText().toString();
        mUpdate.setEnabled(false);
        mDisposable.add(mViewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->mUpdate.setEnabled(true),
                        throwable -> Log.e(TAG, "Unable to update username", throwable)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisposable.clear();
    }
}
