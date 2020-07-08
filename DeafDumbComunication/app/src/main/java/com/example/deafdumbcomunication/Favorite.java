package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Favorite extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    private android.speech.tts.TextToSpeech mTTS;

    private RecyclerView recyclerView;

    private DatabaseReference reference;
    private StorageReference mStorageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ///////////////////////
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Favorite.this));
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
                        Intent i = new Intent(Favorite.this , MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_add:
                        Intent ii = new Intent(Favorite.this , Add_New.class);
                        startActivity(ii);
                        break;
                    case R.id.nav_fav:
                        Intent iii = new Intent(Favorite.this , Favorite.class);
                        startActivity(iii);
                        break;
                    case R.id.nav_signout:
                        i = new Intent(Favorite.this , Login_Form.class);
                        startActivity(i);
                        break;

                }
            }
        });



        ////////////////////////




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

        final users users = new users();

        float pitch = (float) 1.0;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) 1.0;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        //////////////

        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Fovorite");
        FirebaseRecyclerOptions<FovoriteItemsDetail> options = new FirebaseRecyclerOptions.Builder<FovoriteItemsDetail>()
                .setQuery(query, FovoriteItemsDetail.class)
                .build();
        FirebaseRecyclerAdapter<FovoriteItemsDetail , ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<FovoriteItemsDetail, ItemViewHolder>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull final Favorite.ItemViewHolder itemViewHolder, final int position, @NonNull final FovoriteItemsDetail items) {


                        itemViewHolder.imageName.setText(items.getItem_Name());
                        Glide.with(Favorite.this)
                                .load(items.getImage_id())
                                .into(itemViewHolder.imageView);

                        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mTTS.speak( items.getItem_Desc()+itemViewHolder.quantity.getText() +"عدد  ", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);


                                Toast.makeText(Favorite.this ,   items.getItem_Desc()+itemViewHolder.quantity.getText() +"عدد  " , Toast.LENGTH_SHORT).show();

                            }
                        });


                        Drawable d = getResources().getDrawable(R.drawable.ic_favorite_red_24dp);
                        itemViewHolder.favt.setBackground(d);
                        final Query applesQuery = reference.child("Fovorite").orderByChild("item_Name").equalTo(items.getItem_Name());
                        itemViewHolder.favt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Favorite.this , "Item Removed from Fovorite" , Toast.LENGTH_SHORT).show();
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {


                                    }
                                });
                            }
                        });
                        //fovorite button end

                    }

                    @NonNull
                    @Override
                    public Favorite.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
                        return new Favorite.ItemViewHolder(view);
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();


        ///////////////


    }


    public  static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView imageName ;
        ImageView imageView;
        View View;
        EditText quantity;
        Button favt;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageName = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            quantity = itemView.findViewById(R.id.quantity);
            favt = itemView.findViewById(R.id.favBtn);


            this.View = itemView;

        }
    }

}

