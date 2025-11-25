package com.example.wordgame;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreActivity extends AppCompatActivity {

    private static final String FILE_NAME = "highscores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        LinearLayout container = findViewById(R.id.highScoreContainer);

        ArrayList<ScoreEntry> scores = loadScoresFromFile();

        if (scores.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("No highscores saved yet!");
            tv.setTextSize(18f);
            tv.setTextColor(Color.DKGRAY);
            tv.setPadding(8, 16, 8, 16);
            container.addView(tv);
            return;
        }

        // SORT DESCENDING (highest score first)
        Collections.sort(scores, (a, b) -> Integer.compare(b.score, a.score));

        for (ScoreEntry entry : scores) {
            TextView tv = new TextView(this);
            tv.setTypeface(Typeface.MONOSPACE);
            tv.setText(String.format("%-12s   %4d   %s",
                    entry.username, entry.score, entry.date));
            tv.setTextSize(18f);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(6, 18, 6, 18);
            container.addView(tv);
        }
    }

    private ArrayList<ScoreEntry> loadScoresFromFile() {
        ArrayList<ScoreEntry> list = new ArrayList<>();

        try (FileInputStream fis = openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String username = parts[0].trim();
                int score = Integer.parseInt(parts[1].trim());
                String date = parts[2].trim();

                list.add(new ScoreEntry(username, score, date));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static class ScoreEntry {
        String username;
        int score;
        String date;

        ScoreEntry(String u, int s, String d) {
            username = u;
            score = s;
            date = d;
        }
    }

    public void clearHighScores(View view) {
        // Delete the file
        deleteFile(FILE_NAME);

        // Clear the container
        LinearLayout container = findViewById(R.id.highScoreContainer);
        container.removeAllViews();

        // Display a message
        TextView tv = new TextView(this);
        tv.setText("No highscores saved yet!");
        tv.setTextSize(18f);
        tv.setTextColor(Color.DKGRAY);
        tv.setPadding(8, 16, 8, 16);
        container.addView(tv);
    }

    public void goBack(View view) {
        finish();
    }
}
