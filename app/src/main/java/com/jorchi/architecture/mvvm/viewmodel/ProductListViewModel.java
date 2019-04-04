package com.jorchi.architecture.mvvm.viewmodel;

import android.app.Application;

import com.jorchi.architecture.mvvm.BasicApp;
import com.jorchi.architecture.mvvm.DataRepository;
import com.jorchi.architecture.mvvm.db.entity.ProductEntity;
import com.jorchi.architecture.mvvm.model.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class ProductListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;
    private final DataRepository mRepository;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.setValue(null);

        mRepository = ((BasicApp) application).getRepository();
        LiveData<List<ProductEntity>> products = mRepository.getProducts();
        mObservableProducts.addSource(products, mObservableProducts::setValue);
    }

    public LiveData<List<ProductEntity>> getProducts(){
        return mObservableProducts;
    }

    public LiveData<List<ProductEntity>> searchProducts(String query){
        return mRepository.searchProducts(query);
    }
}
