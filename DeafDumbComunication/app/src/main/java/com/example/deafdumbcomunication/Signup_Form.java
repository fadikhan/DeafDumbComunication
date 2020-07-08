package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Form extends AppCompatActivity {

    EditText fullName, userName, email, password, confirmPassword;
    Button btn_register;
    RadioButton radioGenderMale, radioGenderFemale;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);

        fullName = (EditText) findViewById(R.id.fullName);
        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        btn_register = (Button) findViewById(R.id.btn_register);
        radioGenderMale = (RadioButton) findViewById(R.id.radio_male);
        radioGenderFemale = (RadioButton) findViewById(R.id.radio_female);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullname = fullName.getText().toString();
                final String username = userName.getText().toString();
                final String mail = email.getText().toString();
                final String pass = password.getText().toString();
                final String confimPass = confirmPassword.getText().toString();

                if (radioGenderMale.isChecked()){
                    gender = "Male";
                }
                if (radioGenderFemale.isChecked()){
                    gender = "Female";
                }

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(Signup_Form.this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Signup_Form.this, "Please enter Username", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(Signup_Form.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Signup_Form.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(confimPass)){
                    Toast.makeText(Signup_Form.this, " Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                if(pass.length() < 6){
                    Toast.makeText(Signup_Form.this, "Password should be minimum 6 Characters", Toast.LENGTH_LONG).show();
                }

                if(pass.equals(confimPass)) {
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        users information = new users(
                                                fullname,
                                                username,
                                                mail,
                                                pass,
                                                gender
                                        );

                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful())
                                                        {
                                                            Toast.makeText(Signup_Form.this, "Registeration Completed , Please Check ur email", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Login_Form.class));
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Signup_Form.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }




                                                    }
                                                });


                                            }
                                        });
                                    } else {
                                        Toast.makeText(Signup_Form.this, "Auethentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void btn_loginForm(View view) {
        startActivity(new Intent(getApplicationContext(),Login_Form.class));
    }
}
