package com.myproject.myapplication.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myapplication.R;
import com.myproject.myapplication.model.ProductVariant;
import com.myproject.myapplication.model.Store;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.GroceryViewHolder>{
    private List<Store> horizontalGrocderyList;
    Context context;
    RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(Store product);
    }
    public StoreListAdapter(List<Store> horizontalGrocderyList, Context context, RecyclerViewClickListener listener){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
        mListener=listener;

    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_line_item, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        //holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductName());
        Store productInfo =horizontalGrocderyList.get(position);
        String productName = (productInfo.getStoreName() + "-" + productInfo.getCityName());
        holder.tvStoreName.setText(productName);
        holder.tvType.setText(productInfo.getCityName());
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
        TextView tvStoreName,tvType;
        LinearLayout llContainer;
        public GroceryViewHolder(View view) {
            super(view);
            llContainer=view.findViewById(R.id.llContainer);
            tvStoreName=view.findViewById(R.id.tvStoreName);
            tvType=view.findViewById(R.id.tvType);

        }
    }

    public void setData(List<Store> data){
        horizontalGrocderyList=data;
        notifyDataSetChanged();
    }


}