package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;

import java.util.ArrayList;
import java.util.Iterator;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();
        final ArrayList<User> users = new ArrayList<>();
        Button registerBtn = findViewById(R.id.registerBtn);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String username = ds.child("username").getValue(String.class);
                        String password = ds.child("password").getValue(String.class);
                        Integer nrWins = ds.child("nr_wins").getValue(Integer.class);
                        Integer nrLosses = ds.child("nr_losses").getValue(Integer.class);
                        Integer totalTime = ds.child("total_time").getValue(Integer.class);
                        User user = new User(username, password, nrWins, nrLosses, totalTime);
                        users.add(user);
//                        Log.d("TAG", username);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText user = findViewById(R.id.user);
                String auser = user.getText().toString();
                EditText passwd = findViewById(R.id.password);
                String password = passwd.getText().toString();
                EditText passwd2 = findViewById(R.id.password2);
                String password2 = passwd2.getText().toString();

                MD5 md5Hasher = new MD5(password);
                String hashedPasswd = md5Hasher.getMD5();

                if (password.equals(password2)) {

                    boolean checkUser = auser.matches("[a-zA-Z0-9]{6,20}");
                    boolean checkPass = password.matches("[a-zA-Z0-9]{6,20}");
                    if (checkUser && checkPass) {
                        boolean found = false;
                        for (User user1 : users)
                            if (user1.getUsername().equals(auser)) {
                                found = true;
                                break;
                            }
                        if (found)
                            Toast.makeText(Register.this, "Username " + auser + " is already taken!", Toast.LENGTH_SHORT).show();
                        else {
                            myRef.child(userId).child("username").setValue(auser);
                            myRef.child(userId).child("password").setValue(hashedPasswd);
                            myRef.child(userId).child("nr_losses").setValue(0);
                            myRef.child(userId).child("nr_wins").setValue(0);
                            myRef.child(userId).child("total_time").setValue(0);
                            Toast.makeText(Register.this, "User " + auser + " was successfully added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else
                        Toast.makeText(Register.this, "Username and passworrd should have between 6 and 20 alfanumeric chars!", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(Register.this, "Different passwords", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
