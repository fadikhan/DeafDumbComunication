package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TwoWayComunication extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Intent i;
    ///////////////
    ImageView texttospeech;
    ImageView speechToText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_comunication);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        i = new Intent(TwoWayComunication.this , MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_add:
                        i = new Intent(TwoWayComunication.this , Add_New.class);
                        startActivity(i);
                        break;
                    case R.id.nav_fav:
                        i = new Intent(TwoWayComunication.this , Favorite.class);
                        startActivity(i);
                        break;
                }
            }
        });



        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DEAF AND DUMB COMMUNICATION");

        texttospeech = (ImageView) findViewById(R.id.text_to_speech);
        texttospeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwoWayComunication.this , TextToSpeech.class);
                startActivity(i);
            }
        });

      speechToText = (ImageView) findViewById(R.id.speechToText);
      speechToText.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(TwoWayComunication.this , SpeechToText.class);
              startActivity(i);
          }
      });

    }
}
