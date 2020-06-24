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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText Uname,Uemail,Upassword,Uphone;
    FirebaseAuth Fauth;
    Button Login,Register;
    FirebaseFirestore FStore;
    String userId;
    String name,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uname=findViewById(R.id.name);
        Uemail=findViewById(R.id.email);
        Upassword=findViewById(R.id.password);
        Uphone=findViewById(R.id.phone);
        Login=findViewById(R.id.login);
        Register=findViewById(R.id.register);
        Fauth=FirebaseAuth.getInstance();
        FStore=FirebaseFirestore.getInstance();

        if (Fauth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),start.class));
            finish();
        }
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Uemail.getText().toString().trim();
                final String pass = Upassword.getText().toString().trim();
                name = Uname.getText().toString().trim();
                num = Uphone.getText().toString().trim();
                if (TextUtils.isEmpty(email))
                {
                    Uemail.setError("Enter valid EMail");
                    return;
                }
                if (TextUtils.isEmpty(pass))
                {
                    Upassword.setError("Enter Valid password");
                    return;
                }
                //user register
                Fauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            userId=Fauth.getCurrentUser().getUid();
                            Toast.makeText(MainActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = FStore.collection("users").document(userId);
                            Map<String,Object> users = new HashMap<>();
                            users.put("name",name);
                            users.put("email",email);
                            users.put("pass",pass);
                            users.put("num",num);
                            documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Firebase Added", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),start.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });
    }
}
