package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {

    public void openWelcomeActivity() {
        Intent intent = new Intent(this, WelcomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)  ;
        startActivityIfNeeded(intent, 0);
        startActivity(intent);
        WinActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Button menuBtn = findViewById(R.id.menuBtn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomeActivity();
            }
        });
    }
}