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
import com.myproject.myapplication.model.ProductInfo;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class InventoryAdapters extends RecyclerView.Adapter<InventoryAdapters.GroceryViewHolder>{
    private List<ProductInfo> horizontalGrocderyList;
    Context context;

    public InventoryAdapters(List<ProductInfo> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inward_add_product, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        //holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductName());
        holder.txtview.setText(horizontalGrocderyList.get(position).getProductName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = horizontalGrocderyList.get(position).getProductName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

            }
        });
        //holder.tvProcprice.setText("Procument Price:"+horizontalGrocderyList.get(position).getProcPrice());

        //holder.tvSellingPrice.setText("Selling Price:"+horizontalGrocderyList.get(position).getSellingPrice());


        //holder.tvQuantity.setText("Quatity:"+horizontalGrocderyList.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        TextView tvProcprice;
        TextView tvSellingPrice;
        TextView tvQuantity;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.idProductName);
//            tvProcprice=view.findViewById(R.id.tvProcprice);
//            tvSellingPrice=view.findViewById(R.id.tvSellingPrice);
//            tvQuantity=view.findViewById(R.id.tvQuantity);

        }
    }

    public void setData(List<ProductInfo> horizontalGrocderyList){
        this.horizontalGrocderyList= horizontalGrocderyList;
    }
}