package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;



import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    ImageView texttoSpeech;

    Intent i;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        i = new Intent(MainActivity.this , MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_add:
                        i = new Intent(MainActivity.this , Add_New.class);
                        startActivity(i);
                        break;
                    case R.id.nav_fav:
                        i = new Intent(MainActivity.this , Favorite.class);
                        startActivity(i);
                        break;
                    case R.id.nav_signout:
                        i = new Intent(MainActivity.this , Login_Form.class);
                        startActivity(i);
                        break;

                }
            }
        });



        toolbar = findViewById(R.id.toolbar);







        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DEAF AND DUMB COMMUNICATION");



        texttoSpeech = findViewById(R.id.texttospeech);

        viewFlipper = findViewById(R.id.fliper);
        int img[] = {R.drawable.one , R.drawable.two , R.drawable.three};

        for(int images:img)
        {
            imageFlipper(images);
        }
    }

    public  void imageFlipper(int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(this , android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this , android.R.anim.slide_out_right);

    }

    public void textToSpech(View v)
    {
        Intent i = new Intent(this,TwoWayComunication.class);
        startActivity(i);
    }

    public  void house(View v)
    {
        Intent i = new Intent(MainActivity.this,House.class);
        startActivity(i);
    }

    public  void officeActivity(View v)
    {
        Intent i = new Intent(MainActivity.this , Office.class);
        startActivity(i);
    }

    public  void  marketActivity(View v)
    {
        Intent i = new Intent(MainActivity.this , Market.class);
        startActivity(i);
    }
}
