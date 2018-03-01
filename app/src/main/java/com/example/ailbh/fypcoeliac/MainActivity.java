package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private FloatingActionButton fab;
    private EditText editText;


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
    }

        //called when user taps the search button
    public void sendSearch(View view)
    {
        Intent intent = new Intent(this, search_results.class);
        String searchString = editText.getText().toString().toLowerCase();
        System.out.println("first search string: " + searchString);
        intent.putExtra("search-string", searchString);
        startActivity(intent);
    }

}

