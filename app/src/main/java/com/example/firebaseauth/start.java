package com.example.firebaseauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class start extends AppCompatActivity {

    EditText numberToAdd;
    TextView Showemail,Showname,Shownum;
    FirebaseAuth fauth;
    FirebaseFirestore fbase;
    String userid,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        numberToAdd=findViewById(R.id.newnum);
        Showemail=findViewById(R.id.Showemail);
        Showname=findViewById(R.id.Showname);
        Shownum=findViewById(R.id.Shownum);
        fauth=FirebaseAuth.getInstance();
        fbase=FirebaseFirestore.getInstance();
        userid=fauth.getCurrentUser().getUid();

        final DocumentReference documentReference=fbase.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                Showemail.setText(documentSnapshot.getString("email"));
                Showname.setText(documentSnapshot.getString("name"));
                Shownum.setText(documentSnapshot.getString("num"));
                number = documentSnapshot.getString("num");
            }
        });
    }

    public void addnumber(View view)
    {
        int numtoadd = Integer.parseInt(numberToAdd.getText().toString().trim());
        int temp = Integer.parseInt(number);
        int sum = temp+numtoadd;
        Shownum.setText(String.valueOf(sum));
    }
    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }
}