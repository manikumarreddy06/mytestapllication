package com.labhyam.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.labhyam.app.R;
import com.labhyam.app.model.NegativeValue;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class PendingInventoryAdapters extends RecyclerView.Adapter<PendingInventoryAdapters.GroceryViewHolder>{
    private List<NegativeValue> horizontalGrocderyList;
    Context context;

    PendingInventoryAdapters.RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(NegativeValue product);
    }

    public PendingInventoryAdapters(List<NegativeValue> horizontalGrocderyList, Context context, PendingInventoryAdapters.RecyclerViewClickListener listener){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;

        mListener=listener;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_item_pening_inventory, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        //holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductName());
        NegativeValue productInfo =horizontalGrocderyList.get(position);
        holder.txtview.setText(productInfo.getProductName()+""+productInfo.getCategory());

       // holder.tvSellingPrice.setText("Procument Price:"+horizontalGrocderyList.get(position).getQuantity());


        holder.tvQuantity.setText("Quantity:"+horizontalGrocderyList.get(position).getQuantity());

        holder.approvebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback to
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
        TextView tvSellingPrice;
        TextView tvQuantity;
        Button approvebButton;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.inwardproductname);
            tvSellingPrice=view.findViewById(R.id.tvPrice);
            tvQuantity=view.findViewById(R.id.tvQty);
            approvebButton=view.findViewById(R.id.btnapprove);

        }
    }

    public void setData(List<NegativeValue> horizontalGrocderyList){
        this.horizontalGrocderyList= horizontalGrocderyList;
    }

    public void clearData(){
        this.horizontalGrocderyList.clear();
    }



}