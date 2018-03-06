package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductPage extends AppCompatActivity {

    private String name;
    private String brand;
    private String type;
    private String category;
    private String size;

    private TextView productLabel;
    private TextView productName;
    private TextView productBrand;
    private TextView productType;
    private TextView productCategory;
    private TextView productSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        productLabel = findViewById(R.id.productLabel);
        productName = findViewById(R.id.productName);
        productBrand = findViewById(R.id.productBrand);
        productSize = findViewById(R.id.productSize);
        productCategory = findViewById(R.id.productCategory);
        productType = findViewById(R.id.productType);

        Bundle extras = getIntent().getExtras();
        productLabel.setText((extras.getString("PRODUCT_BRAND") + " " + extras.getString("PRODUCT_NAME")));
        productName.setText("Name: " + extras.getString("PRODUCT_NAME"));
        productBrand.setText("Brand: " + extras.getString("PRODUCT_BRAND"));
        productType.setText("Type: " + extras.getString("PRODUCT_TYPE"));
        productCategory.setText("Category: " + extras.getString("PRODUCT_CAT"));
        productSize.setText("Size: " + extras.getString("PRODUCT_SIZE"));
    }
}
