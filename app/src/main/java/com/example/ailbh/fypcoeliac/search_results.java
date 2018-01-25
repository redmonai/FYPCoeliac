package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class search_results extends AppCompatActivity {
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        System.out.println("created screen 2");
        //Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if(extras != null)
//        {
//            search = extras.getString("string-search");
//        }
//        Intent intent = getIntent();
//        String search = intent.getStringExtra("search-string");
//        System.out.println("Passed string: " + search);

//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(search);
    }
}
