package com.example.ailbh.fypcoeliac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void dietInfo(View view)
    {
        Intent intent = new Intent(this, DietInfoScreen.class);
        startActivity(intent);
    }

    public void CDInfo(View view)
    {
        Intent intent = new Intent(this, CDInfoScreen.class);
        startActivity(intent);
    }

    public void LabellingInfo(View view)
    {
        Intent intent = new Intent(this, LabellingInfoScreen.class);
        startActivity(intent);
    }

    public void OatsInfo(View view)
    {
        Intent intent = new Intent(this, OatsInfoScreen.class);
        startActivity(intent);
    }

    public void Disclaimer(View view)
    {
        Intent intent = new Intent(this, DisclaimerScreen.class);
        startActivity(intent);
    }
}
