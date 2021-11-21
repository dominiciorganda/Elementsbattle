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

public class LogInActivity extends AppCompatActivity {


    public void openRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openWelcomeActivity(Intent i) {
        startActivity(i);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();
        Button registerBtn = findViewById(R.id.registerBtn);
        Button loginBtn = findViewById(R.id.loginBtn);
        final ArrayList<User> users = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    users.clear();
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
                openRegisterActivity();

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.user);
                String auser = user.getText().toString();
                EditText pass = findViewById(R.id.passwd);
                String password = pass.getText().toString();

                MD5 md5Hasher = new MD5(password);
                String hashedPasswd = md5Hasher.getMD5();

                boolean found = false;
                String username = "";
                for (User user1 : users) {
                    if (user1.getUsername().equals(auser) && user1.getPassword().equals(hashedPasswd)) {
                        username = user1.getUsername();
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Intent i = new Intent(getBaseContext(), WelcomePage.class);
                    i.putExtra("USER_NAME", username);
                    openWelcomeActivity(i);
                } else
                    Toast.makeText(LogInActivity.this, "Wrong login credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
