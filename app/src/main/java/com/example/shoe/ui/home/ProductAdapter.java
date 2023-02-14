package com.example.shoe.ui.home;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoe.Admin.AdminMaintainProductsActivity;
import com.example.shoe.HomeActivity;
import com.example.shoe.Interface.ItemClickListener;
import com.example.shoe.Model.Products;
import com.example.shoe.ProductDetailsActivity;
import com.example.shoe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewviewHolder>  {
    List productDataList;
    public ItemClickListener listener;
    Context context;
    String type;

    public ProductAdapter(List productDataList,Context context,String type) {
        this.productDataList = productDataList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_items_layout, viewGroup, false);
        return new MyViewviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewviewHolder viewHolder, int i) {
        final Products data = (Products) productDataList.get(i);
        viewHolder.txtProductName.setText(data.getName());
//                         viewHolder.txtProductDescription.setText(data.getDescription());
        Picasso.get().load(data.getImage()).into(viewHolder.imageView);
        viewHolder.txtProductId.setText(data.getProductId());
        if(data.isIfDiscounted())
        {

            viewHolder.txtProductPrice.setText(data.getPrice() + "$ Price");
            viewHolder.txtProductSalePrice.setText(data.getSalePrice() + "$ OnSale");
        }
        else
        {

            viewHolder.txtProductSalePrice.setVisibility(View.GONE);
            viewHolder.txtProductPrice.setText(data.getPrice() + "$ Price");
            viewHolder.txtProductPrice.setBackgroundResource(0);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!type.equals("Admin"))
                {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("productId", data.getProductId());
                    context.startActivity(intent);

                }
                else
                {
                    Intent intent = new Intent(context, AdminMaintainProductsActivity.class);
                    intent.putExtra("productId", data.getProductId());
                    context.startActivity(intent);
                }

//                                 Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
//                                 intent.putExtra("productId", data.getProductId());
//                                 startActivity(intent);
            }
        });
       /* viewHolder.favLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Products products : idList)
                {
                    if(viewHolder.txtProductName.getText().toString().equals(products.getName()))
                    {
                        Log.d("fdkjfsdf",products.getName());
                        addToFavourite(products);
                    }
                }

            }
        });*/

    }



    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public void setItemClickListener (ItemClickListener listener)
    {
        this.listener = listener;
    }


    class MyViewviewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductSalePrice,txtProductId;
        public ImageView imageView,favouriteImageView;
        public LinearLayout favLayout;

        public MyViewviewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            txtProductName = (TextView) itemView.findViewById(R.id.product_name);
            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            txtProductSalePrice = (TextView) itemView.findViewById(R.id.product_sale_price);
            txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
            favouriteImageView = itemView.findViewById(R.id.favouriteIcon);
            txtProductId = itemView.findViewById(R.id.productId);
            favLayout = itemView.findViewById(R.id.favLayout);
        }
    }
}
