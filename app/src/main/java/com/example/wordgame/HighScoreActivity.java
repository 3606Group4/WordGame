package com.example.wordgame;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    private static final String FILE_NAME = "highscores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        LinearLayout container = findViewById(R.id.highScoreContainer);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnClear = findViewById(R.id.btnClearScores);

        ArrayList<HighScore> highScores = loadHighScores();

        // Display scores dynamically
        for (HighScore score : highScores) {
            TextView tv = new TextView(this);
            tv.setText(score.toString());
            tv.setTextSize(18f);
            tv.setPadding(8, 8, 8, 8);
            container.addView(tv);
        }

        btnBack.setOnClickListener(v -> finish());

        btnClear.setOnClickListener(v -> {
            clearHighScoreFile();
            container.removeAllViews();
        });
    }

    private ArrayList<HighScore> loadHighScores() {
        ArrayList<HighScore> list = new ArrayList<>();

        try (FileInputStream fis = openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new HighScore(parts[0], Integer.parseInt(parts[1]), parts[2]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private void clearHighScoreFile() {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write("".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
