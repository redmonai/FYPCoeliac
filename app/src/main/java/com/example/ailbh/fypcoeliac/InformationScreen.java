package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformationScreen extends AppCompatActivity {
    private static final String TAG = "InformationScreen";

    private BottomNavigationView bottomNavView;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, String> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_screen);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.exLV);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_info_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(InformationScreen.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(InformationScreen.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(InformationScreen.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //current screen
                        break;
                    case R.id.nav_profile_id:
                        //to profile screen
                        Intent intentProf = new Intent(InformationScreen.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

//        textView = findViewById(R.id.body);
//        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, String>();

        // Adding child data
        listDataHeader.add(getResources().getString(R.string.gluten_info));
        listDataHeader.add(getResources().getString(R.string.gf_diet));
        listDataHeader.add(getResources().getString(R.string.coeliac_info));
        listDataHeader.add(getResources().getString(R.string.labelling_info));
        listDataHeader.add(getResources().getString(R.string.gf_oats));
        listDataHeader.add(getResources().getString(R.string.disclaimer));

        listDataChild.put(listDataHeader.get(0), getResources().getString(R.string.gluten_info_text)); // Header, Child data
        listDataChild.put(listDataHeader.get(1), getResources().getString(R.string.gf_diet_info));
        listDataChild.put(listDataHeader.get(2), getResources().getString(R.string.coeliac_info_text));
        listDataChild.put(listDataHeader.get(3), getResources().getString(R.string.labelling_info_text));
        listDataChild.put(listDataHeader.get(4), getResources().getString(R.string.gf_oats_info_text));
        listDataChild.put(listDataHeader.get(5), getResources().getString(R.string.disclaimer_text));
    }
}
