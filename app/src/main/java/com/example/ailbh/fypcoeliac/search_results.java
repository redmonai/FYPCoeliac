package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class search_results extends AppCompatActivity {
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get the Intent that started this activity and extract the string
        Bundle extras = getIntent().getExtras();
        search = extras.getString("search-string");

        System.out.println("Passed string: " + search);

//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(search);
    }
}
