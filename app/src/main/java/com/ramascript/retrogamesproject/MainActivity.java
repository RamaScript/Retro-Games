package com.ramascript.retrogamesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public void login(View view){
        Intent intent = new Intent(this, mainLogin.class);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent = new Intent(this, mainProfile.class);
        startActivity(intent);
    }



    public void Snake(View view){
        Intent i = new Intent(this,snakeMain.class);
        startActivity(i);
    }

    public void flappy(View view){
        Intent intent = new Intent(this, FBmain.class);
        startActivity(intent);
    }

    public void ttts(View view){
        Intent intent = new Intent(this, TTTSAddPlayers.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}