package com.labhyam.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.labhyam.app.R;
import com.labhyam.app.model.SalesInfo;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class ProfitDetailLineAdapters extends RecyclerView.Adapter<ProfitDetailLineAdapters.GroceryViewHolder>{
    private List<SalesInfo> horizontalGrocderyList;
    Context context;

    public ProfitDetailLineAdapters(List<SalesInfo> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_line_item, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        //holder.imageView.setImageResource(horizontalGrocderyList.get(position).getProductName());
        SalesInfo productInfo =horizontalGrocderyList.get(position);
        holder.txtview.setText(productInfo.getProductName());


        holder.tvSellingPrice.setText(horizontalGrocderyList.get(position).getProfit());
        holder.tvLabelPrice.setText("Profit");
        holder.tvQuantity.setText("Sold Quantity : "+horizontalGrocderyList.get(position).getCountOfSale());

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
        TextView tvLabelPrice;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.inwardproductname);
            tvSellingPrice=view.findViewById(R.id.tvPrice);
            tvQuantity=view.findViewById(R.id.tvQty);
            tvLabelPrice=view.findViewById(R.id.tvLabelPrice);



        }
    }


}