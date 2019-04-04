package com.jorchi.architecture.mvvm;

import com.jorchi.architecture.mvvm.db.AppDatabase;
import com.jorchi.architecture.mvvm.db.entity.CommentEntity;
import com.jorchi.architecture.mvvm.db.entity.ProductEntity;
import com.jorchi.architecture.mvvm.model.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class DataRepository {
    private static DataRepository mInstance;
    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public static DataRepository getInstance(final AppDatabase database){
        if (mInstance == null){
            synchronized (DataRepository.class){
                if (mInstance == null){
                    mInstance = new DataRepository(database);
                }
            }
        }
        return mInstance;
    }

    public DataRepository(AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(), productEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null){
                mObservableProducts.postValue(productEntities);
            }
        });
    }

    public LiveData<List<ProductEntity>> getProducts(){
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId){
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId){
        return mDatabase.commentDao().loadComments(productId);
    }

    public LiveData<List<ProductEntity>> searchProducts(String query){
        return mDatabase.productDao().searchAllProducts(query);
    }

}
