package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {
    private FirebaseUser user;

    private BottomNavigationView bottomNavView;

    private TextView usernameText;
    private TextView useremailText;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //set up navbar
        bottomNavView = (BottomNavigationView) findViewById(R.id.mainNav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavView);
        bottomNavView.setSelectedItemId(R.id.nav_profile_id);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_search_id:
                        //to search screen
                        Intent intentSearch = new Intent(UserProfile.this, MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_cat_id:
                        //to category screen
                        Intent intentCat = new Intent(UserProfile.this, ViewCategoriesActivity.class);
                        startActivity(intentCat);
                        break;
                    case R.id.nav_scan_id:
                        //to scanning screen
                        Intent intentScan = new Intent(UserProfile.this, scanIngredient.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_info_id:
                        //to info screen
                        Intent intentInfo = new Intent(UserProfile.this, InformationScreen.class);
                        startActivity(intentInfo);
                        break;
                    case R.id.nav_profile_id:
                        //current screen
                        break;
                }
                return false;
            }
        });

        //FirebaseAuth
        user = FirebaseAuth.getInstance().getCurrentUser();

        usernameText = (TextView) findViewById(R.id.usernameText);
        useremailText = (TextView) findViewById(R.id.useremailText);
        usernameText.setText(user.getDisplayName());
        useremailText.setText(user.getEmail());

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(UserProfile.this);
                Intent intentMain = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intentMain);
            }
        });

    }
}
