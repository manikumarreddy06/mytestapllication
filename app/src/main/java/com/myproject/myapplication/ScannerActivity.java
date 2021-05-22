package com.myproject.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.Result;
import com.myproject.myapplication.adapters.ProductListAdapter;
import com.myproject.myapplication.model.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public  class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static final int WRITE_EXST = 1;
    private static final int REQUEST_PERMISSION = 123;
    int CAMERA;
    String position, formt;


    private List<ProductDetails> productList = new ArrayList<>();
    private RecyclerView groceryRecyclerView;
    private ProductListAdapter groceryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 5);
            }
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

        groceryRecyclerView = findViewById(R.id.rvcontent);
        // add a divider after each item for more clarity
        groceryRecyclerView.addItemDecoration(new DividerItemDecoration(ScannerActivity.this, LinearLayoutManager.HORIZONTAL));
        groceryAdapter = new ProductListAdapter(productList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(ScannerActivity.this, LinearLayoutManager.HORIZONTAL, false);
        groceryRecyclerView.setLayoutManager(horizontalLayoutManager);
        groceryRecyclerView.setAdapter(groceryAdapter);
        populategroceryList();
    }


    private void populategroceryList() {
        ProductDetails potato = new ProductDetails("Potato");
        ProductDetails onion = new ProductDetails("Onion");
        ProductDetails cabbage = new ProductDetails("Cabbage");
        ProductDetails cauliflower = new ProductDetails("Cauliflower");
        productList.add(potato);
        productList.add(onion);
        productList.add(cabbage);
        productList.add(cauliflower);
        groceryAdapter.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        position=rawResult.getText();
        formt=rawResult.getBarcodeFormat().toString();
        Intent intent=new Intent();
        intent.putExtra("Contents",position);
        intent.putExtra("Format",formt);
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public void handleResult(MediaBrowserServiceCompat.Result rawResult) {

    }
}