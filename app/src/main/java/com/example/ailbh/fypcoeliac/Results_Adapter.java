package com.example.ailbh.fypcoeliac;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ailbh on 13/02/2018.
 */

public class Results_Adapter extends ArrayAdapter<Product>
{
    private ArrayList<Product> results;
    private final Activity search_results;
    private final ListView results_list;

    public Results_Adapter(Activity search_results, ArrayList<Product> results, ListView results_list)
    {
        super(search_results, R.layout.activity_search_results, (List<Product>) search_results);

        this.search_results = search_results;
        this.results = results;
        this.results_list = results_list;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // get the product for this position
        Product current = getItem(position);

        // check if an existing view is being reused, otherwise inflate the view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_search_results, parent, false);
        }
        // lookup view for data population
        TextView prodName = (TextView) convertView.findViewById(R.id.prodName);
        TextView prodBrand = (TextView) convertView.findViewById(R.id.prodBrand);

        // populate the data into the template view using the data object
        prodName.setText(current.prodName);
        prodBrand.setText(current.brand);

        //return the completed view to render on screen
        return convertView;
    }

}
