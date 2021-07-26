package com.myproject.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myapplication.R;
import com.myproject.myapplication.model.NegativeValue;
import com.myproject.myapplication.model.ProductInfo;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class PendingInventoryAdapters extends RecyclerView.Adapter<PendingInventoryAdapters.GroceryViewHolder>{
    private List<NegativeValue> horizontalGrocderyList;
    Context context;

    public PendingInventoryAdapters(List<NegativeValue> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String productName = horizontalGrocderyList.get(position).getVariantId().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

            }
        });
        //holder.tvProcprice.setText("Procument Price:"+horizontalGrocderyList.get(position).getProcPrice());

       //holder.tvSellingPrice.setText(""+horizontalGrocderyList.get(position).getVariantId());

        holder.tvQuantity.setText("Quantity:"+horizontalGrocderyList.get(position).getQuantity());

        holder.approvebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback to

                String productName = horizontalGrocderyList.get(position).getVariantId().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

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
        TextView tvProcprice;
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



}