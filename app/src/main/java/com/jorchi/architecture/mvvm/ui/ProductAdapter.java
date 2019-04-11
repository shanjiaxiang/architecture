package com.jorchi.architecture.mvvm.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jorchi.architecture.R;
import com.jorchi.architecture.databinding.ProductItemBinding;
import com.jorchi.architecture.mvvm.model.Product;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemViewHolder> {

    List<? extends Product> mProductList;
    private final ProductClickCallback mProductClickCallback;

    public ProductAdapter(ProductClickCallback mProductClickCallback) {
        this.mProductClickCallback = mProductClickCallback;
        setHasStableIds(true);
    }

    public void setProductList(List<? extends Product> productList){
        if (mProductList == null){
            mProductList = productList;
            notifyItemRangeChanged(0, productList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProductList.get(oldItemPosition).getId()
                            == productList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product newProduct = productList.get(newItemPosition);
                    Product oldProduct = mProductList.get(oldItemPosition);
                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getDescription(), oldProduct.getDescription())
                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && Objects.equals(newProduct.getPrice(), oldProduct.getPrice());
                }
            });
            mProductList = productList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.product_item, parent, false);
        binding.setCallback(mProductClickCallback);
        return new ProductItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        holder.binding.setProduct(mProductList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList== null? 0 : mProductList.size();
    }

    public static class ProductItemViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding binding;

        public ProductItemViewHolder(ProductItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
