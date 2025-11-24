package com.example.wordgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    private static final String FILE_NAME = "highscores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        String username = getIntent().getStringExtra("username");

        TextView resultText = findViewById(R.id.txtResult);
        EditText phoneInput = findViewById(R.id.phoneNumber);
        Button shareBtn = findViewById(R.id.btnShare);
        Button viewHighscores = findViewById(R.id.btnViewScores);

        // Get existing highscore from file
        int highScore = getHighScore();

        // Show results including highscore
        String displayText = "Player: " + username +
                "\nScore: " + score +
                "\nHighscore: " + highScore;

        resultText.setText(displayText);

        // Save this new score to file
        saveHighscore(username, score);

        // SHARE BUTTON — now includes highscore
        shareBtn.setOnClickListener(v -> {
            String phoneNumber = phoneInput.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                phoneInput.setError("Enter a phone number");
                return;
            }

            String msg = "Player: " + username +
                    "\nScore: " + score +
                    "\nHighscore: " + highScore;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + phoneNumber));
            intent.putExtra("sms_body", msg);
            startActivity(intent);
        });

        // VIEW HIGHSCORES BUTTON
        viewHighscores.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });
    }

    // Save score to file
    private void saveHighscore(String username, int score) {
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String record = username + "," + score + "," + date + "\n";

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
            fos.write(record.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ highscore from file
    private int getHighScore() {
        int highest = 0;

        try (FileInputStream fis = openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int score = Integer.parseInt(parts[1]);
                    if (score > highest) {
                        highest = score;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return highest;
    }

    public void playAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
