package com.example.deafdumbcomunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Form extends AppCompatActivity {


    EditText txtEmail, txtPassword;
    Button btn_login;
    private FirebaseAuth firebaseAuth;
    users users = new users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
        txtEmail = (EditText) findViewById(R.id.lemail);
        txtPassword = (EditText) findViewById(R.id.lpassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login_Form.this, "Email is missing", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login_Form.this, "Password is missing", Toast.LENGTH_SHORT).show();;
                }
                if(password.length() < 6){
                    Toast.makeText(Login_Form.this, "Password should be more than 5 Characters", Toast.LENGTH_LONG).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                                    {
                                        users.setEmail(email);
                                     //   Toast.makeText(Login_Form.this, "Welcome" + users.getEmail(), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(Login_Form.this, "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                                    }



                                } else {
                                    Toast.makeText(Login_Form.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }

    public void btn_signupForm(View view) {

        Intent i = new Intent(Login_Form.this , Signup_Form.class);
        startActivity(i);
    }
}
