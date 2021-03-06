package com.labhyam.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.labhyam.app.R;
import com.labhyam.app.model.ProductVariant;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.GroceryViewHolder>{
    private List<ProductVariant> horizontalGrocderyList;
    Context context;
    RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(ProductVariant product);
    }
    public ProductListAdapter(List<ProductVariant> horizontalGrocderyList, Context context, RecyclerViewClickListener listener){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
        mListener=listener;

    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_line_item, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        //holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductName());
        ProductVariant productInfo =horizontalGrocderyList.get(position);
        String productName = (productInfo.getProductVariantName() + "-" + productInfo.getUnits() + "" + productInfo.getUnitType());
        holder.txtview.setText(productName);
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(horizontalGrocderyList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }



    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        LinearLayout llContainer;
        public GroceryViewHolder(View view) {
            super(view);
            llContainer=view.findViewById(R.id.llContainer);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.idProductName);

        }
    }


}