package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ProductPage extends AppCompatActivity {

    private static final String TAG = "ProductPage";

    private BottomNavigationView bottomNavView;

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

        //Set up navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_cat_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(ProductPage.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(ProductPage.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(ProductPage.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(ProductPage.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //to profile screen
                        Intent intentProf = new Intent(ProductPage.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

        productLabel = findViewById(R.id.productLabel);
        productName = findViewById(R.id.productName);
        productBrand = findViewById(R.id.productBrand);
        productSize = findViewById(R.id.productSize);
        productCategory = findViewById(R.id.productCategory);
        productType = findViewById(R.id.productType);

        Bundle extras = getIntent().getExtras();
        productLabel.setText((extras.getString("PRODUCT_BRAND") + " - " + extras.getString("PRODUCT_NAME")));
        productName.setText(extras.getString("PRODUCT_NAME"));
        productBrand.setText(extras.getString("PRODUCT_BRAND"));
        productType.setText(extras.getString("PRODUCT_TYPE"));
        productCategory.setText(extras.getString("PRODUCT_CAT"));
        productSize.setText(extras.getString("PRODUCT_SIZE"));
    }
}
