package com.example.wordgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
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

        resultText.setText(getString(R.string.result_text, username, score));

        saveHighscore(username, score);

        shareBtn.setOnClickListener(v -> {
            String phoneNumber = phoneInput.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                phoneInput.setError("Enter a phone number");
                return;
            }

            String msg = getString(R.string.sms_message_basic, username, score);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + phoneNumber));
            intent.putExtra("sms_body", msg);
            startActivity(intent);
        });

        viewHighscores.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });

    }

    private void saveHighscore(String username, int score) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String record = username + "," + score + "," + date + "\n";

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
            fos.write(record.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
