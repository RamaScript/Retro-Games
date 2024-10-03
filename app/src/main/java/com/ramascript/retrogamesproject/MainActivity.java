package com.ramascript.retrogamesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    public void simonSays(View view){
        Intent intent = new Intent(this, simonGame.class);
        startActivity(intent);
    }

    ImageButton profileBtn, chatBtn;

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

        profileBtn = findViewById(R.id.profile_btn);
        chatBtn = findViewById(R.id.chat_btn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, mainProfile.class);
                startActivity(intent);
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "In Developement", Toast.LENGTH_SHORT).show();
            }
        });

    }
}