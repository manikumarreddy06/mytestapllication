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

public class ProductInWardAdapters extends RecyclerView.Adapter<ProductInWardAdapters.GroceryViewHolder>{
    private List<ProductVariant> horizontalGrocderyList;
    Context context;

    public ProductInWardAdapters(List<ProductVariant> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inwarddynamic, parent, false);
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

            }
        });
        holder.tvProcprice.setText("Price:"+horizontalGrocderyList.get(position).getProcPrice());
//
//        holder.tvSellingPrice.setText("Selling Price:"+horizontalGrocderyList.get(position).getSellingPrice());
//
//
        holder.tvQuantity.setText("quantity:"+horizontalGrocderyList.get(position).getQuantity()+"  "+horizontalGrocderyList.get(position).getUnitType());

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
            txtview=view.findViewById(R.id.productName);
            tvProcprice=view.findViewById(R.id.tvPrice);
//            tvSellingPrice=view.findViewById(R.id.tvSellingPrice);
            tvQuantity=view.findViewById(R.id.tvQty);

        }
    }
}