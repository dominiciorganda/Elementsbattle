package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    public void openChangePassActivity(Intent i){
        startActivity(i);
    }

    public void openChangeUsernameActivity(Intent i){
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Intent intent = getIntent();
        final String username = intent.getStringExtra("USER_NAME");

        Button changePassBtn = findViewById(R.id.changeUsrBtn);

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ChangePasswordActivity.class);
                i.putExtra("USER_NAME", username);
                openChangePassActivity(i);
            }
        });

        Button changeNameButton = findViewById(R.id.changeNameButton);

        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ChangeUsernameActivity.class);
                i.putExtra("USER_NAME", username);
                openChangeUsernameActivity(i);
            }
        });
        
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    final ArrayList<User> users = new ArrayList<>();
                    users.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String username = ds.child("username").getValue(String.class);
                        String password = ds.child("password").getValue(String.class);
                        Long nrWins = ds.child("nr_wins").getValue(Long.class);
                        Long nrLosses = ds.child("nr_losses").getValue(Long.class);
                        Long totalTime = ds.child("total_time").getValue(Long.class);

                        User user = new User(username, password, nrWins.intValue(), nrLosses.intValue(), totalTime.intValue());
                        users.add(user);
                    }

                    User myUser = new User();
                    for (User userFound : users) {
                        if (userFound.getUsername().equals(username)) {
                            myUser.setUsername(userFound.getUsername());
                            myUser.setNrWins(userFound.getNrWins());
                            myUser.setNrLosses(userFound.getNrLosses());
                            myUser.setTimePlayed(userFound.getTimePlayed());
                        }
                    }

                    System.out.println(myUser);

                    TextView textViewUsername = findViewById(R.id.textViewUsername);
                    textViewUsername.setText(myUser.getUsername());

                    TextView nrWinsTextView = findViewById(R.id.textViewNumberWins);
                    nrWinsTextView.setText("" + myUser.getNrWins());

                    TextView nrLosesTextView = findViewById(R.id.textView3);
                    nrLosesTextView.setText("" + myUser.getNrLosses());

                    TextView ratio = findViewById(R.id.textViewRatio);
                    if (myUser.getNrLosses() != 0) {
                        double wlRatio = (double) myUser.getNrWins() / (double) myUser.getNrLosses();
                        wlRatio*=100;
                        double display = Math.round(wlRatio);
                        display/=100;
                        ratio.setText("" + display);
                    } else {
                        ratio.setText("" + myUser.getNrWins());
                    }

                    TextView totalTimeTextView = findViewById(R.id.textViewTotalTime);
                    totalTimeTextView.setText("" + myUser.getTimePlayed());

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}