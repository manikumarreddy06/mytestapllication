package com.labhyam.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.labhyam.app.ProductUtils;
import com.labhyam.app.R;
import com.labhyam.app.Utils;
import com.labhyam.app.model.ProductVariant;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class CheckoutLineItemAdapter extends RecyclerView.Adapter<CheckoutLineItemAdapter.GroceryViewHolder>{
    private List<ProductVariant> horizontalGrocderyList;
    Context context;
    float quantity=0;
    RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void updateTotalPrice();
    }

    public CheckoutLineItemAdapter(List<ProductVariant> horizontalGrocderyList, Context context, RecyclerViewClickListener listener){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
        mListener=listener;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkoutlineitem, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {

        quantity=0;

        ProductVariant productInfo =horizontalGrocderyList.get(position);
        String productName = (productInfo.getProductVariantName() + "-" + productInfo.getUnits() + "" + productInfo.getUnitType());
        holder.txtview.setText(productName);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = horizontalGrocderyList.get(position).getProductVariantName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

            }
        });


        holder.tvQuantity.setText(horizontalGrocderyList.get(position).getQuantity()+"");


        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=Float.parseFloat(holder.tvQuantity.getText().toString())-1;
                if(quantity>0) {
                    productInfo.setQuantity(quantity);
                    ProductUtils.instance(context).updateQty(productInfo);
                    holder.tvQuantity.setText(quantity+"");
                }
                else{
                    Utils.Companion.toast("quantity should be  more than zero",context);
                }
                mListener.updateTotalPrice();
            }
        });

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Float.parseFloat(holder.tvQuantity.getText().toString()) + 1;
                holder.tvQuantity.setText(quantity + "");
                productInfo.setQuantity(quantity);
                ProductUtils.instance(context).updateQty(productInfo);

                mListener.updateTotalPrice();

            }
        });

        if(ProductUtils.instance(context).isOutOrderTypeFlag()){
            holder.tvLabelPrice.setText(productInfo.getSellingPrice()+"");
        }
        else{
            holder.tvLabelPrice.setText(productInfo.getProcPrice()+"");
        }

    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public List<ProductVariant> getProductItems(){
        return horizontalGrocderyList;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        TextView tvQuantity;

        TextView tvMinus;
        TextView tvPlus;
        TextView tvLabelPrice;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.idProductImage);
            txtview=view.findViewById(R.id.inwardproductname);
            tvQuantity=view.findViewById(R.id.etProductQty);


            tvMinus=view.findViewById(R.id.minus);
            tvPlus=view.findViewById(R.id.plus);
            tvLabelPrice=view.findViewById(R.id.tvLabelPrice);

        }

    }
}