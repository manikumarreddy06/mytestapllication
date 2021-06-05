package com.myproject.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myapplication.R;
import com.myproject.myapplication.model.ProductVariant;

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
        holder.txtview.setText(horizontalGrocderyList.get(position).getProductVariantName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = horizontalGrocderyList.get(position).getProductVariantName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

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
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.idProductName);

        }
    }


}