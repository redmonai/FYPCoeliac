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

    private BottomNavigationView bottomNavView;
    private String ingredients;
    private String trimIngredients;
    private TextView ingredientTextView;
    private TextView wheatTV;
    private TextView barleyTV;
    private TextView ryeTV;
    private TextView oatsTV;

    private String startMatch = "ingredient";

    private boolean wheat;
    private boolean barley;
    private boolean rye;
    private boolean oats;

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
        System.out.println("trimmed ingredient string " + trimIngredients);

        //display string
        ingredientTextView = (TextView) findViewById(R.id.ingredientTextView);
        ingredientTextView.setMovementMethod(new ScrollingMovementMethod());
        ingredientTextView.setText(trimIngredients);

        //todo intepret string

        wheatTV = (TextView) findViewById(R.id.containsWheatCheck);
        barleyTV = (TextView) findViewById(R.id.containsBarleyCheck);
        ryeTV = (TextView) findViewById(R.id.containsRyeCheck);
        oatsTV = (TextView) findViewById(R.id.containsOatsCheck);

        ingredientsCheck(wheatTV, "wheat");
        ingredientsCheck(barleyTV, "barley");
        ingredientsCheck(ryeTV, "rye");
        ingredientsCheck(oatsTV, "oat");
    }

    private void ingredientsCheck(TextView textView, String ingredient)
    {
        if (trimIngredients.toLowerCase().contains(ingredient))
        {
            textView.setText("Ingredients mention " + ingredient);
        }
        else
        {
            textView.setText("Ingredients do not mention " + ingredient);
        }
    }
}
