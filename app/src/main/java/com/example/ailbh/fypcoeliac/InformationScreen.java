package com.example.ailbh.fypcoeliac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformationScreen extends AppCompatActivity {
 //   private TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;

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

//        textView = findViewById(R.id.body);
//        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, String>();

        // Adding child data
        listDataHeader.add(getResources().getString(R.string.gf_diet));
        listDataHeader.add(getResources().getString(R.string.coeliac_info));
        listDataHeader.add(getResources().getString(R.string.labelling_info));
        listDataHeader.add(getResources().getString(R.string.gf_oats));
        listDataHeader.add(getResources().getString(R.string.disclaimer));

        listDataChild.put(listDataHeader.get(0), getResources().getString(R.string.gf_diet_info)); // Header, Child data
        listDataChild.put(listDataHeader.get(1), getResources().getString(R.string.coeliac_info_text));
        listDataChild.put(listDataHeader.get(2), getResources().getString(R.string.labelling_info_text));
        listDataChild.put(listDataHeader.get(3), getResources().getString(R.string.gf_oats_info_text));
        listDataChild.put(listDataHeader.get(4), getResources().getString(R.string.disclaimer_text));
    }
}
