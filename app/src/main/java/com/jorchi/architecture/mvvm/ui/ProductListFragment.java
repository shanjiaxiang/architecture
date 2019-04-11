package com.jorchi.architecture.mvvm.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorchi.architecture.MainActivity;
import com.jorchi.architecture.R;
import com.jorchi.architecture.databinding.FragmentProductListBinding;
import com.jorchi.architecture.mvvm.db.entity.ProductEntity;
import com.jorchi.architecture.mvvm.model.Product;
import com.jorchi.architecture.mvvm.viewmodel.ProductListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class ProductListFragment extends Fragment {
    public static final String TAG = "ProductListFragment";
    private ProductAdapter mProductAdapter;
    private FragmentProductListBinding mBinding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);
        mProductAdapter = new ProductAdapter(mProductClickCallback);
        mBinding.productList.setAdapter(mProductAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProductListViewModel viewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        mBinding.productsSearchBtn.setOnClickListener(v -> {
            Editable query = mBinding.productSearchBox.getText();
            if (query == null || query.toString().trim().isEmpty()){
                subscribeUi(viewModel.getProducts());
            }else {
                subscribeUi(viewModel.searchProducts("*"+query+"*"));
            }
        });
        subscribeUi(viewModel.getProducts());
    }

    private void subscribeUi(LiveData<List<ProductEntity>> products) {
        products.observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                if (productEntities != null){
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(productEntities);
                }else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    private final ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
                ((MainActivity)getActivity()).show(product);
            }
        }
    };
}
