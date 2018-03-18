package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewCategoriesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;

    private String[] categories;
    private ListView categoryListView;
    private ArrayAdapter<String> catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_cat_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(ViewCategoriesActivity.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //current screen
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(ViewCategoriesActivity.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(ViewCategoriesActivity.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //to profile screen
                        Intent intentProf = new Intent(ViewCategoriesActivity.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

        categoryListView = findViewById(R.id.CategoryListView);
        categories = getResources().getStringArray(R.array.food_categories);
        catAdapter = new ArrayAdapter<String>(ViewCategoriesActivity.this, android.R.layout.simple_list_item_1, categories);
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
}
