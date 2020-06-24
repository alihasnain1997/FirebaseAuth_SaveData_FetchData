package com.example.firebaseauth;

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

public class login extends AppCompatActivity {

    EditText Lemail,Lpass;
    Button login,register;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Lemail=findViewById(R.id.Loginemail);
        Lpass=findViewById(R.id.Loginpassword);
        login=findViewById(R.id.login);
        register=findViewById(R.id.REgister_page);
        fAuth= FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Lemail.getText().toString().trim();
                String pass = Lpass.getText().toString().trim();
                if (TextUtils.isEmpty(email))
                {
                    Lemail.setError("Enter valid EMail");
                    return;
                }
                if (TextUtils.isEmpty(pass))
                {
                    Lpass.setError("Enter Valid password");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete())
                        {
                            Toast.makeText(login.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),start.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(login.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

}