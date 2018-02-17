package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
    private ArrayList<Product> products;
    private ListView resultsListView;
    private Results_Adapter resultsAdapter;
//    private ArrayAdapter<Product> adapter;
    private TextView textView;
    private TextView searchBar;
    public static final int NUM_PRODUCTS = 469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //initialise views
        searchBar = (TextView) findViewById(R.id.searchBar2);
        resultsListView = findViewById(R.id.resultsListView);

        //Get the Intent that started this activity and extract the string
        Bundle extras = getIntent().getExtras();
        search = extras.getString("search-string").toLowerCase();
        searchBar.setText(search);
        System.out.println("Passed string: " + search);

        // Initialise results listview and its adapter
        List<Product> resultsList = new ArrayList<>();
        resultsAdapter = new Results_Adapter(this, R.layout.productrow, resultsList);
        resultsListView.setAdapter(resultsAdapter);

        //products = new ArrayList();

//        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, products);
//        resultsListView.setAdapter(adapter);

        getData();

        textView = findViewById(R.id.resultsCount);
        textView.setText(resultsList.size() + " results");

        // Pass number of results to the screen
        //textView = findViewById(R.id.resultsCount);
        //textView.setText(products.size() + " results");
    }

    public void getData()
    {
        products = new ArrayList<Product>();
        int i = 0;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();

        mDatabaseRef.child(Integer.toString(i)).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product newProd = dataSnapshot.getValue(Product.class);
                    System.out.println("From firebase: " + newProd.name);

                    //check if the product pulled from firebase matches the search string and,
                    // if not present in list, adds it
                    //string.equalsIgnoreCase(search)
                    if (((newProd.name.toLowerCase()).contains(search))
                            || ((newProd.brand.toLowerCase()).contains(search))
                            && (!products.contains(newProd)))
//                    if ((StringUtils.containsIgnoreCase(newProd.name, search))
//                            ||
//                            )
                    {
                        products.add(newProd);
                        resultsAdapter.add(newProd);
                        //adapter.notifyDataSetChanged();
                        System.out.println("added to list!");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("READ_FAILED", "Read failed");
            }
        });
    }

}
