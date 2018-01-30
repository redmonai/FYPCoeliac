package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_results extends AppCompatActivity {
    private String search;
    private DatabaseReference mDatabase;
    private ArrayList products;

    public static final int NUM_PRODUCTS = 469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get the Intent that started this activity and extract the string
        Bundle extras = getIntent().getExtras();
        search = extras.getString("search-string").toLowerCase();

        System.out.println("Passed string: " + search);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("");
        System.out.println("DatabaseReference " + ref.toString());
        int i = 0;
        FirebaseDatabase.getInstance().getReference().child(Integer.toString(i))
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product newProd = new Product();
                            newProd.prodName = dataSnapshot.child("Product").getValue(String.class);
                            newProd.brand = dataSnapshot.child("Brand").getValue(String.class);
                            newProd.category = dataSnapshot.child("Food Category").getValue(String.class);
                            newProd.type = dataSnapshot.child("Type of Food").getValue(String.class);
                            newProd.size = dataSnapshot.child("Size").getValue(String.class);

                            //snapshot.getValue(Product.class);
                            System.out.println(newProd.prodName);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        products = new ArrayList<Product>();


//        ref.child("messages").on("value", function(snapshot) {
//            console.log("There are "+snapshot.numChildren()+" messages");
//        })








//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(search);
    }
}
