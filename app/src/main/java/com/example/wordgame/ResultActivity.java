package com.example.wordgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        String username = getIntent().getStringExtra("username");

        TextView resultText = findViewById(R.id.txtResult);
        EditText phoneInput = findViewById(R.id.phoneNumber);
        Button shareBtn = findViewById(R.id.btnShare);

        resultText.setText(getString(R.string.result_text, username, score));

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
    }
}
