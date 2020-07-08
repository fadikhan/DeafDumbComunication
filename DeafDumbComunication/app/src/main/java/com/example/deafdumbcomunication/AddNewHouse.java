package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddNewHouse extends AppCompatActivity {

     private static final int PICK_IMAGE_REQUEST = 7;
     private ImageView chooseImage;
     private  Button uploadImage;
     private EditText item_name;
     private EditText item_description;
     private ImageView imageDisplay;
     private Uri mImageUri;
    String Storage_Path = "HouseImages/";
    Items items;



     private StorageReference storageReference;
     private DatabaseReference databaseReference;
     private StorageTask uploadTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_house);
        users users = new users();

         chooseImage = findViewById(R.id.select_product_image);
         uploadImage = findViewById(R.id.add_new_product);

         item_description = findViewById(R.id.product_description);
         item_name= findViewById(R.id.product_name);

         imageDisplay = findViewById(R.id.select_product_image);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("House");



     chooseImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


                 OpenFileChooser();


         }
     });


     uploadImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(uploadTask != null && uploadTask.isInProgress())
             {
                 Toast.makeText(AddNewHouse.this , "Upload in progress please wait" , Toast.LENGTH_SHORT).show();
             }

             else {
                 uploadFile();
             }
         }
     });
    }

    private  void OpenFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
        && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageDisplay);

        }

    }

    private  String getExtention(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private  void uploadFile() {

        if (mImageUri != null) {


            // Creating second StorageReference.
        final StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + getExtention(mImageUri));

        // Adding addOnSuccessListener to second StorageReference.
        uploadTask = storageReference2nd.putFile(mImageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageReference2nd.getDownloadUrl();
            }
        })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            String TempImageName = item_name.getText().toString().trim();
                            String TempDes = item_description.getText().toString().trim();
                            Items imageUploadInfo = new Items(TempImageName, task.getResult().toString() , TempDes);
                            //  Uri downloadUri = task.getResult();
                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                            Toast.makeText(AddNewHouse.this , "Data Successfully Uploaded" , Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText(AddNewHouse.this , "Error While Uploaded" , Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    }
}
