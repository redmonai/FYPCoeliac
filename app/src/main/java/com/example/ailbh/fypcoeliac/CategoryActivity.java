package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "categoryAcivity";
    private String viewSelected;

    private BottomNavigationView bottomNavView;

    private String category;
    private TextView categoryLabel;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private Results_Adapter resultsAdapter;
    private List<Product> resultsList;
    private ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        viewSelected = "nav_cat_id";

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
                        Intent intentSearch = new Intent(CategoryActivity.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(CategoryActivity.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(CategoryActivity.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(CategoryActivity.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //to profile screen
                        Intent intentProf = new Intent(CategoryActivity.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

        // get passed category
        Bundle extras = getIntent().getExtras();
        category = extras.getString("CATEGORY");

        // initialise category label and set text
        categoryLabel = findViewById(R.id.categoryLabel);
        categoryLabel.setText(category);

        // Initialise results listview and its adapter
        resultsListView = findViewById(R.id.catListView);
        resultsList = new ArrayList<>();
        resultsAdapter = new Results_Adapter(this, R.layout.productrow, resultsList);
        resultsListView.setAdapter(resultsAdapter);

        getData();

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = resultsList.get(position);

                Intent intent =  new Intent(getApplicationContext(), ProductPage.class);
                intent.putExtra("PRODUCT_NAME", product.name);
                intent.putExtra("PRODUCT_BRAND", product.brand);
                intent.putExtra("PRODUCT_CAT", product.category);
                intent.putExtra("PRODUCT_SIZE", product.size);
                intent.putExtra("PRODUCT_TYPE", product.type);
                intent.putExtra("PRODUCT_KEY", product.key);
                intent.putExtra("SOURCE_PAGE", viewSelected);
                startActivity(intent);
            }
        });
    }

    public void getData()
    {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();

        mDatabaseRef.child("food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product newProd = snapshot.getValue(Product.class);
                    newProd.key = snapshot.getKey().toString();
                    if (category.equals(newProd.type.toString()))
                    {
                        resultsAdapter.add(newProd);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("READ_FAILED", "Read failed");
            }
        });
    }



}
