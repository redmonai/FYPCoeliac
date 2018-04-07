package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class IngredientInterpreter extends AppCompatActivity {

    private static final String wheat = "wheat";
    private static final String barley = "barley";
    private static final String rye = "rye";
    private static final String oat = "oat";

    private BottomNavigationView bottomNavView;

    private String ingredients;
    private String trimIngredients;
    private String lowerIngredients;

    private TextView ingredientTextView;
    private TextView glutenTV;
    private TextView wheatTV;
    private TextView barleyTV;
    private TextView ryeTV;
    private TextView oatsTV;

    private String startMatch = "ingredient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_interpreter);

        //set up navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_scan_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(IngredientInterpreter.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(IngredientInterpreter.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        Intent intentScan = new Intent(IngredientInterpreter.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(IngredientInterpreter.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //to scanning screen
                        Intent intentProf = new Intent(IngredientInterpreter.this, UserProfile.class);
                        startActivity(intentProf);
                        break;
                }
                return false;
            }
        });

        //get passed ingredient string
        Bundle extras = getIntent().getExtras();
        ingredients = extras.getString("INGREDIENT_STRING");

        int startPos = ingredients.toLowerCase().indexOf(startMatch);

        trimIngredients = ingredients.substring(startPos);

        //display string
        ingredientTextView = (TextView) findViewById(R.id.ingredientTextView);
        ingredientTextView.setMovementMethod(new ScrollingMovementMethod());
        ingredientTextView.setText(trimIngredients);

        lowerIngredients = trimIngredients.toLowerCase();

        glutenTV = (TextView) findViewById(R.id.containsGlutenCheck);
        wheatTV = (TextView) findViewById(R.id.containsWheatCheck);
        barleyTV = (TextView) findViewById(R.id.containsBarleyCheck);
        ryeTV = (TextView) findViewById(R.id.containsRyeCheck);
        oatsTV = (TextView) findViewById(R.id.containsOatsCheck);

        glutenCheck(glutenTV);
        wheatCheck(wheatTV);
        ingredientsCheck(barleyTV, barley);
        ingredientsCheck(ryeTV, rye);
        ingredientsCheck(oatsTV, oat);
    }

    private void ingredientsCheck(TextView textView, String ingredient)
    {
        if (lowerIngredients.contains(ingredient))
        {
            textView.setText("  Ingredients mention " + ingredient);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.x38, 0, 0, 0);
        }
        else
        {
            textView.setText("  Ingredients do not mention " + ingredient);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check38, 0, 0, 0);
        }
    }

    private void wheatCheck(TextView textView)
    {
        if (lowerIngredients.contains(" " + wheat))
        {
            textView.setText("  Ingredients mention wheat");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.x38, 0, 0, 0);
        }
        else
        {
            textView.setText("  Ingredients do not mention wheat");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check38, 0, 0, 0);
        }
    }

    private void glutenCheck(TextView textView)
    {
        if (lowerIngredients.contains("gluten free") || trimIngredients.toLowerCase().contains("gluten-free"))
        {
            textView.setText("  Ingredients include gluten free label");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check38, 0, 0, 0);
        }
        else if (lowerIngredients.contains("gluten"))
        {
            textView.setText("  Ingredients mention gluten");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.x38, 0, 0, 0);
        }
        else
        {
            textView.setText("  Ingredients do not mention gluten");
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check38, 0, 0, 0);
        }
    }
}
