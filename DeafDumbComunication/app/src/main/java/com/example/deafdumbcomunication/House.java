package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class House extends AppCompatActivity  {

    //Toolbar toolbar;

    private BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    private android.speech.tts.TextToSpeech mTTS;

    private  RecyclerView recyclerView;

    private DatabaseReference reference;
    private StorageReference mStorageRef;



    //////////////////////

    DatabaseReference ref ;
    FovoriteItemsDetail member;
   // users users = new users();

    /////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
          //////////////////////////
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(House.this));
        reference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("DEAF AND DUMB COMMUNICATION");

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        Intent i = new Intent(House.this , MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_add:
                        Intent ii = new Intent(House.this , Add_New.class);
                        startActivity(ii);
                        break;
                    case R.id.nav_fav:
                        Intent iii = new Intent(House.this , Favorite.class);
                        startActivity(iii);
                        break;

                    case R.id.nav_signout:
                        i = new Intent(House.this , Login_Form.class);
                        startActivity(i);
                        break;

                }
            }
        });

        //////////////
        //Toast.makeText(House.this , users.getEmail() , Toast.LENGTH_SHORT).show();

        ref =FirebaseDatabase.getInstance().getReference("Fovorite");//.child(users.getFullName());
        member = new FovoriteItemsDetail();
        ////////////
        mTTS = new android.speech.tts.TextToSpeech(this, new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == android.speech.tts.TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(new Locale("ur"));
                    if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA
                            || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {

                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        users users = new users();
        float pitch = (float) 1.0;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) 1.0;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("House");
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();
        FirebaseRecyclerAdapter<Items ,ItemViewHolder > adapter =
                new FirebaseRecyclerAdapter<Items, ItemViewHolder>(options ) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int position, @NonNull final Items items) {


                        itemViewHolder.imageName.setText(items.getItem_Name());
                        Glide.with(House.this)
                                .load(items.getImage_id())
                                .into(itemViewHolder.imageView);

                        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mTTS.speak( items.getItem_Desc()+itemViewHolder.quantity.getText() +"عدد  ", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);


                                Toast.makeText(House.this ,   items.getItem_Desc()+itemViewHolder.quantity.getText() +"عدد  " , Toast.LENGTH_SHORT).show();

                            }
                        });



                        //fovorite button start
                        itemViewHolder.fvtBtn.setOnClickListener(new View.OnClickListener() {



                            @Override
                            public void onClick(View v) {




                                Drawable d = getResources().getDrawable(R.drawable.ic_favorite_red_24dp);

                                   itemViewHolder.fvtBtn.setBackground(d);
                                   itemViewHolder.fvtBtn.setEnabled(false);

                                   member.setItem_Name(items.getItem_Name());
                                   member.setItem_Desc(items.getItem_Desc());
                                   member.setImage_id(items.getImage_id());
                                   ref.push().setValue(member);
                                Toast.makeText(House.this , "Added To Fovorite",Toast.LENGTH_SHORT).show();



                            }
                            //on click end

                        });

                     //fovorite button end

                    }

                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
                        return new ItemViewHolder(view);
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    public  static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView imageName ;
        ImageView imageView;
        View View;
        EditText quantity;
        Button fvtBtn;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            quantity = itemView.findViewById(R.id.quantity);
            fvtBtn = itemView.findViewById(R.id.favBtn);
            this.View = itemView;

        }
    }


    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }


    //////////////////////////////////////





    ///////////////////////////////


}
