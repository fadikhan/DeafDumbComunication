package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Add_New extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Intent i;

    ImageView home , market , office;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new);

        ////////////////////
        home = (ImageView) findViewById(R.id.home);
        market = (ImageView) findViewById(R.id.market);
        office = (ImageView) findViewById(R.id.office);
        /////////////////

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 i = new Intent(Add_New.this ,AddNewHouse.class);
                 startActivity(i);

            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Add_New.this , AddNewMarket.class);
                startActivity(i);
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Add_New.this , AddNewOffice.class);
                startActivity(i);
            }
        });






        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        i = new Intent(Add_New.this , MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_add:
                        i = new Intent(Add_New.this , Add_New.class);
                        startActivity(i);
                        break;
                    case R.id.nav_fav:
                        i = new Intent(Add_New.this , Favorite.class);
                        startActivity(i);
                        break;

                    case R.id.nav_signout:
                        i = new Intent(Add_New.this , Login_Form.class);
                        startActivity(i);
                        break;
                }
            }
        });



        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DEAF AND DUMB COMMUNICATION");
    }
}
