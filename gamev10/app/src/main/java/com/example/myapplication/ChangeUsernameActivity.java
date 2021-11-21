package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChangeUsernameActivity extends AppCompatActivity {

    public void openProfileActivity(Intent i) {
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        //Firebase reference
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();

        //get user from Toast
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("USER_NAME");
        //Toast.makeText(ChangePasswordActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        final EditText newUsernameText = findViewById(R.id.changeUsernameText);
        Button submitBtn = findViewById(R.id.changeUsernameButton);

        //clicklistener
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newUsername = newUsernameText.getText().toString();

                boolean checkUsername = newUsername.matches("[a-zA-Z0-9]{6,20}");

                if (!checkUsername) {
                    Toast.makeText(ChangeUsernameActivity.this, "Username should have between 6 and 20 alfanumeric chars!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        try {
                            List<String> usernames = new ArrayList<>();
                            DataSnapshot currentUser = null;

                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String username = ds.child("username").getValue(String.class);

                                usernames.add(username);

                                if (getIntent().getStringExtra("USER_NAME").equals(username)) {
                                    currentUser = ds;

                                    if (newUsername.equals(username)) {
                                        Toast.makeText(ChangeUsernameActivity.this, "New username should be diffrent than the current username!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            if (usernames.contains(newUsername)) {
                                Toast.makeText(ChangeUsernameActivity.this, "This username is already taken. Please pick a diffrent one!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            currentUser.child("username").getRef().setValue(newUsername);

                            Toast.makeText(ChangeUsernameActivity.this, "Username successfully changed", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                            i.putExtra("USER_NAME", newUsername);
                            openProfileActivity(i);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}