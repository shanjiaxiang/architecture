package com.jorchi.architecture;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jorchi.architecture.mvvm.model.Product;
import com.jorchi.architecture.mvvm.ui.ProductFragment;
import com.jorchi.architecture.mvvm.ui.ProductListFragment;
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
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG)
                    .commit();
        }
    }


    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container, productFragment, null)
                .commit();

    }
}
