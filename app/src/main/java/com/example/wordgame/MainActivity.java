package com.example.wordgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private TextView rule;
    private Button game, highScores;


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
        username=findViewById(R.id.userName);
        game=findViewById(R.id.startGame);
        highScores=findViewById(R.id.highScore);
        rule=findViewById(R.id.rules);

        highScores.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });

        /*rule.setText("You have 50 seconds to guess the Word" +
                    "\nAfter every 10 seconds a picture hint will be revealed" +
                    "\nThe player has 3 Attempts " +
                    "\nAfter 3 attempts the player scored is calculated and the game ends" +
                    "\nThe player has 3 Attempts "+
                    "\nPoints are as Followed: "+
                    "\nMore than 40 sec Remaining 20 points"+
                    "\nLess than 40 sec Remaining 15 points"+
                    "\nLess than 30 sec Remaining 10 points"+
                    "\n Less than 20 sec Remaining 15 points"+
                    "\nLess than 10 sec Remaining 1 points"
        );*/

    }
    public void StartGame(View view) {
        String userName =username.getText().toString();
        if(userName.isEmpty()){
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), PlayGame.class);
        intent.putExtra("username", String.valueOf(userName));
        startActivity(intent);
       /*
        Rules
        You have 50 seconds to guess the Word
        After every 10 seconds a picture hint will be revealed
        The player has 3 Attempts
        After 3 attempts the player scored is calculated and the game ends
        Points are as Followed:
        More than 40 sec Remaining 20 points
        Less than 40 sec Remaining 15 points
        Less than 30 sec Remaining 10 points
        Less than 20 sec Remaining 15 points
        Less than 10 sec Remaining 1 points
        */

    }
}