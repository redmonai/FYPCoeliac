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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ArrayList<String> favouritesIndex;
    private ArrayList<Product> favourites;
    private Results_Adapter favouritesAdapter;

    private BottomNavigationView bottomNavView;
    private String viewSelected;

    private TextView usernameText;
    private TextView useremailText;
    private Button signOutButton;
    private ListView favouritesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        viewSelected = "nav_profile_id";

        favouritesListView = findViewById(R.id.favouritesListView);

        // Initialise results listview and its adapter
        favourites = new ArrayList<Product>();
        favouritesAdapter = new Results_Adapter(this, R.layout.productrow, favourites);
        favouritesListView.setAdapter(favouritesAdapter);

        favouritesIndex = new ArrayList<String>();
        getFavourites();

        //set up navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_profile_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(UserProfile.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(UserProfile.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(UserProfile.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(UserProfile.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //current screen
                        break;
                }
                return false;
            }
        });

        //FirebaseAuth
        user = FirebaseAuth.getInstance().getCurrentUser();
        usernameText = (TextView) findViewById(R.id.usernameText);
        useremailText = (TextView) findViewById(R.id.useremailText);
        usernameText.setText("Username: " + user.getDisplayName());
        useremailText.setText("Email: " + user.getEmail());

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(UserProfile.this);
                Intent intentMain = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intentMain);
            }
        });

        //resultsListView onClickListener to take user to product page
        favouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = favourites.get(position);

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

    public void getFavourites()
    {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseRef = mFirebaseDatabase.getReference();

        //find the keys of the user's favourites
        mDatabaseRef.child("users").child(userID).child("favourites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue().toString().equals("true")) {
                        favouritesIndex.add(snapshot.getKey().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("READ_FAILED", "Read failed");
            }
        });

        //find the product records of the user favourites
        mDatabaseRef.child("food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey().toString();
                    for (int i = 0; i < favouritesIndex.size(); i++) {
                        if (key.equals(favouritesIndex.get(i))) {
                            Product newProd = snapshot.getValue(Product.class);
                            newProd.addKey(snapshot.getKey().toString());
                            favouritesAdapter.add(newProd);
                        }
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
