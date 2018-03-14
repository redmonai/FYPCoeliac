package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private FloatingActionButton fab;
    private EditText editText;
    private String[] categories;
    private ListView categoryListView;
    private ArrayAdapter<String> catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InformationScreen.class);
                startActivity(intent);
            }
        });

        editText = (EditText) findViewById(R.id.searchBar1);

        categoryListView = findViewById(R.id.CategoryListView);
        categories = getResources().getStringArray(R.array.food_categories);
        catAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, categories);
        categoryListView.setAdapter(catAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String category = categories[position].trim();
                Intent intent =  new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra("CATEGORY", category);
                startActivity(intent);
            }
        });
    }

    //called when user taps the search button
    public void sendSearch(View view)
    {
        Intent intent = new Intent(this, search_results.class);
        String searchString = editText.getText().toString().toLowerCase();
        intent.putExtra("SEARCH_STRING", searchString);
        startActivity(intent);
    }

    //called when user taps the scan ingredients button
    public void scanIngredient(View view)
    {
        Intent intent = new Intent(this, scanIngredient.class);
        startActivity(intent);
    }

}

