package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductPage extends AppCompatActivity {

    private static final String TAG = "ProductPage";

    private BottomNavigationView bottomNavView;
    private Button addFavButton;
    private Boolean favourite;

    private String key;

    private TextView categoryLabel;
    private TextView productLabel;
    private TextView productName;
    private TextView productBrand;
    private TextView productCategory;
    private TextView productSize;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private String userID;

    private int resourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        Bundle extras = getIntent().getExtras();

        //Set up navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);

        resourceId = this.getResources().getIdentifier(extras.getString("SOURCE_PAGE"), "id", this.getPackageName());
        bottomNavView.setSelectedItemId(resourceId);

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
        productCategory = findViewById(R.id.productType);
        categoryLabel = findViewById(R.id.categoryLabel);
        productLabel.setText((extras.getString("PRODUCT_BRAND") + " - " + extras.getString("PRODUCT_NAME")));
        productName.setText(extras.getString("PRODUCT_NAME"));
        productBrand.setText(extras.getString("PRODUCT_BRAND"));
        productCategory.setText(extras.getString("PRODUCT_CAT"));
        productSize.setText(extras.getString("PRODUCT_SIZE"));
        categoryLabel.setText(extras.getString("PRODUCT_TYPE"));
        key = (extras.getString("PRODUCT_KEY"));


        addFavButton = (Button) findViewById(R.id.addFavButton);
        favourite = false;

        //determine if the product has been favourited or not and draw relevant button
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("users").child(userID).child("favourites");
        mDatabaseRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null || !dataSnapshot.getValue().toString().equals("false")) {
                    favourite = true;
                }
                updateFaveButton();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("READ_FAILED", "Read failed");
            }
        });

        addFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favourite) {
                    //add to favourites
                    mDatabaseRef.child(key).setValue("true");
                    favourite = true;
                    updateFaveButton();
                }
                else
                {
                    //remove from favourites
                    mDatabaseRef.child(key).setValue("false");
                    favourite = false;
                    updateFaveButton();
                }
            }
        });
    }

    public void updateFaveButton()
    {
        if (favourite) {
            addFavButton.setText("Remove from Favourites");
        } else {
            addFavButton.setText("Add to Favourites");
        }
    }

}
