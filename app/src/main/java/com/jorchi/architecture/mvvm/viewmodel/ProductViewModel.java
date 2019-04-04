package com.jorchi.architecture.mvvm.viewmodel;

import android.app.Application;

import com.jorchi.architecture.mvvm.DataRepository;
import com.jorchi.architecture.mvvm.db.entity.CommentEntity;
import com.jorchi.architecture.mvvm.db.entity.ProductEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductViewModel extends AndroidViewModel {

    private final LiveData<ProductEntity> mObservableProduct;
    private final LiveData<List<CommentEntity>> mObservableComments;

    public ObservableField<ProductEntity> product = new ObservableField<>();
    private final int mProductId;

    public ProductViewModel(@NonNull Application application, DataRepository repository, final int productId) {
        super(application);
        mProductId = productId;
        mObservableProduct = repository.loadProduct(productId);
        mObservableComments = repository.loadComments(productId);
    }

    public LiveData<List<CommentEntity>> getComments(){
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableProduct(){
        return mObservableProduct;
    }

    public void setProduct(ProductEntity product){
        this.product.set(product);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        private final Application mApplication;
        private final int mProductId;
        private final DataRepository mRepository;

        public Factory(Application mApplication, int mProductId, DataRepository mRepository) {
            this.mApplication = mApplication;
            this.mProductId = mProductId;
            this.mRepository = mRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductViewModel(mApplication, mRepository, mProductId);
        }
    }

}
