package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 123;
    private String mUsername;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;

    //UI Elements
    private BottomNavigationView bottomNavView;
    private EditText searchBar;
    private ListView resultsListView;
    private TextView resultsCountText;
    private Button searchButton;

    //Search Elements
    private List<Product> resultsList;
    private int resultsCount;
    private Results_Adapter resultsAdapter;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login
        mUsername = "Anonymous";
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    //user is signed in
                    onSignedInInitialise(user.getDisplayName());
                }
                else
                {
                    onSignedOutCleanup();
                    //user is signed out
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        //navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_search_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //current screen
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(MainActivity.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(MainActivity.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(MainActivity.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //to profile screen
                        Intent intentProf = new Intent(MainActivity.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

        //search results
        searchBar = (EditText) findViewById(R.id.searchBar);
        resultsListView = findViewById(R.id.resultsListView);
        searchButton = findViewById(R.id.searchButton);

        // Initialise results listview and its adapter
        resultsList = new ArrayList<>();
        resultsAdapter = new Results_Adapter(this, R.layout.productrow, resultsList);
        resultsListView.setAdapter(resultsAdapter);
        resultsCount = 0;
        resultsCountText = findViewById(R.id.resultsCount);

        //searchButton onClickListener to search the database
        searchButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        resultsList.clear();
                        resultsAdapter.notifyDataSetChanged();
                        searchString = searchBar.getText().toString().trim().toLowerCase();
                        getData();
                    }
                }
        );

        //resultsListView onClickListener to take user to product page
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

                startActivity(intent);
            }
        });
    }

    //Database Search Methods
    //gets data from firebase if it matches passed search string
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
                    //check if the product pulled from firebase matches the search string and,
                    // if not present in list, add it
                    if ((((newProd.name.trim().toLowerCase()).contains(searchString))
                            || ((newProd.brand.trim().toLowerCase()).contains(searchString)))
                            && (!listContains(resultsList, newProd)))
                    {
                        resultsAdapter.add(newProd);
                    }

                }
                resultsCount = resultsList.size();
                resultsCountText.setText(resultsCount + " results");
                resultsCountText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("READ_FAILED", "Read failed");
            }
        });

    }

    //checks the results list to see if the product has already been added
    public boolean listContains(List<Product> products, Product newProd)
    {
        for (int i = 0; i < products.size(); i++)
        {
            Product temp = products.get(i);
            if (temp.name.equals(newProd.name) || temp.brand.equals(newProd.brand))
                return true;
        }
        return false;
    }

    //Firebase Auth methods
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //activity in background - remove auth state listener
    @Override
    protected void onPause()
    {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    //activity in foreground - add auth state listener
    @Override
    protected void onResume()
    {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialise(String username)
    {
        mUsername = username;
    }

    private void onSignedOutCleanup()
    {
        mUsername = "Anonymous";
    }

}

