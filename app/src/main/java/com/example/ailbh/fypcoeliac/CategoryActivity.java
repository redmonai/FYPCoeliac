package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CategoryActivity extends AppCompatActivity {

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle extras = getIntent().getExtras();
        category = extras.getString("CATEGORY");
        System.out.println("Category: " + category);
    }
}
