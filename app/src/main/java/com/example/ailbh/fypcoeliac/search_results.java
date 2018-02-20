package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class search_results extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private String search;
    private List<Product> resultsList;
    private ListView resultsListView;
    private int resultsCount;
    private Results_Adapter resultsAdapter;
    private TextView resultsCountText;
    private EditText searchBar;
    public static final int NUM_PRODUCTS = 469;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //initialise views
        searchBar = (EditText) findViewById(R.id.searchBar2);
        resultsListView = findViewById(R.id.resultsListView);

        //Get the Intent that started this activity and extract the string
        Bundle extras = getIntent().getExtras();
        search = extras.getString("search-string").toLowerCase();
        searchBar.setText(search);

        // Initialise results listview and its adapter
        resultsList = new ArrayList<>();
        resultsAdapter = new Results_Adapter(this, R.layout.productrow, resultsList);
        resultsListView.setAdapter(resultsAdapter);
        resultsCount = 0;
        resultsCountText = findViewById(R.id.resultsCount);

        getData();
    }

    public void getData()
    {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();
        for (int i = 0; i < NUM_PRODUCTS; i++)
        {
            mDatabaseRef.child(Integer.toString(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product newProd = dataSnapshot.getValue(Product.class);

                        //check if the product pulled from firebase matches the search string and,
                        // if not present in list, adds it
                        if (((newProd.name.toLowerCase()).contains(search))
                                || ((newProd.brand.toLowerCase()).contains(search))
                                && (!resultsList.contains(newProd)))
                        {
                            resultsList.add(newProd);
                            resultsAdapter.add(newProd);
                        }

                    }
                    resultsCount = resultsList.size();
                    resultsCountText.setText(resultsCount + " results");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("READ_FAILED", "Read failed");
                }
            });
        }
    }

    //called when user taps the search button
    public void sendSearch(View view)
    {
        Intent intent = new Intent(this, search_results.class);
        String searchString = searchBar.getText().toString().toLowerCase();
        intent.putExtra("search-string", searchString);
        System.out.println("new search string: " + searchString);
        startActivity(intent);
    }

}
