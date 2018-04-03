package com.example.ailbh.fypcoeliac;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by ailbh on 13/02/2018.
 */

public class Results_Adapter extends ArrayAdapter<Product>
{

    public Results_Adapter(Context context, int resource, List<Product> results)
    {
        super(context, resource, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.productrow, parent, false);
        }

        TextView productNameView = (TextView) convertView.findViewById(R.id.productNameView);
        TextView productBrandView = (TextView) convertView.findViewById(R.id.productBrandView);

        Product product = getItem(position);

        productNameView.setVisibility(View.VISIBLE);
        productNameView.setText(product.name);
        productBrandView.setVisibility(View.VISIBLE);
        productBrandView.setText(product.brand);

        return convertView;
    }

}
