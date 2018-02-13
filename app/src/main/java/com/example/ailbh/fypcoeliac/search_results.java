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

public class search_results extends AppCompatActivity {
    private String search;
    private DatabaseReference mDatabase;
    private ArrayList<Product> products;
    private ListView results;
    private ArrayAdapter<Product> adapter;
    private TextView textView;

    public static final int NUM_PRODUCTS = 469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get the Intent that started this activity and extract the string
        Bundle extras = getIntent().getExtras();
        search = extras.getString("search-string").toLowerCase();

        System.out.println("Passed string: " + search);

        results = findViewById(R.id.results);
        products = new ArrayList();

        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, products);
        results.setAdapter(adapter);

        getData();

        // Pass number of results to the screen
        //textView = findViewById(R.id.resultsCount);
        //textView.setText(products.size() + " results");
    }

    public void getData()
    {
        products = new ArrayList<Product>();
        int i = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Integer.toString(i)).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product newProd = new Product();
                    newProd.prodName = dataSnapshot.child("Product").getValue(String.class);
                    newProd.brand = dataSnapshot.child("Brand").getValue(String.class);
                    newProd.category = dataSnapshot.child("Food Category").getValue(String.class);
                    newProd.type = dataSnapshot.child("Type of Food").getValue(String.class);
                    newProd.size = dataSnapshot.child("Size").getValue(String.class);
                    System.out.println("From firebase: " + newProd.prodName);

                    //check if the product pulled from firebase matches the search string and,
                    // if not present in list, adds it
                        if ((newProd.prodName.contains(search) || newProd.brand.contains(search))
                                && !products.contains(newProd))
                        {
                            products.add(newProd);
                            adapter.notifyDataSetChanged();
                            System.out.println("added to list!");
                        }
                    }
                    System.out.println("end of for loop");
                    System.out.println("ArrayList size is: " + products.size());
                    for (int j = 0; j < products.size(); j++)
                    {
                        System.out.println("Array element " + j + " is " + products.get(j).prodName);
                    }
                    //draw ListView here???


//                    textView = findViewById(R.id.resultsCount);
//                    textView.setText(products.size() + " results");
                }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("READ_FAILED", "Read failed");
            }
        });
    }

}
