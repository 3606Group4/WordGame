package com.example.wordgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.os.CountDownTimer;
import android.widget.Toast;

public class PlayGame extends AppCompatActivity {
    private TextView countdown,attempt;
    private ArrayList<Words> words= new ArrayList<>();
    private EditText showWords;
    private ImageView hint1,hint2,hint3,hint4;
    private Button startBtn,compareBtn;
    private int count=0;
    private int lv=0,points=0,wrongAttemp=3;
    private boolean timerOn = false;
    private long timeleft=0;
    CountDownTimer Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        countdown = findViewById(R.id.timer);
        showWords = findViewById(R.id.word);
        attempt = findViewById(R.id.attempts);
        hint1 = findViewById(R.id.image1);
        hint2 = findViewById(R.id.image2);
        hint3 = findViewById(R.id.image3);
        hint4 = findViewById(R.id.image4);
        startBtn = findViewById(R.id.start);
        compareBtn = findViewById(R.id.compare);
        getWordData();
    }
    public void startGame(View view){// This function Starts the Game
        /*if(lv==words.size()-1){//Tells player when it is on the final level;
            Toast.makeText(this,"This is the final level",Toast.LENGTH_SHORT).show();
        }
        if(lv==words.size()){//Tells player when it is on the final level;
            EndGame(view);
        }*/
        attempt.setText("Attempts left : "+wrongAttemp);
        //System.out.println(words.size()+"SIZE"+lv);
        if(Timer!=null){  //Restarts timer when new level starts
            if(timeleft>=40){//Calculates points
                points=points+20;
                System.out.println("total points: "+points);
                timeleft=0;
            }
            if((timeleft>=30)&&(timeleft<40)){
                points=points+15;
                System.out.println("total points: "+points);
                timeleft=0;
            }
            if((timeleft>=20)&&(timeleft<30)){
                points=points+10;
                System.out.println("total points: "+points);
                timeleft=0;
            }
            if((timeleft>=10)&&(timeleft<20)){
                points=points+5;
                System.out.println("total points: "+points);
                timeleft=0;
            }
            if((timeleft>0)&&(timeleft<10)){
                points=points+1;
                System.out.println("total points: "+points);
                timeleft=0;
            }

            Timer.cancel();
        }
        if(lv==words.size()-1){//Tells player when it is on the final level;
            Toast.makeText(this,"This is the final level",Toast.LENGTH_SHORT).show();
        }
        if(lv==words.size()){//Tells player when it is on the final level;
            EndGame(view);
        }
        String showWord;
        // System.out.println("***********");
        ArrayList<Character> wordState = new ArrayList<>();
        // System.out.println(words.get(0).wordName);
        // System.out.println("***********");
        //for(lv=0; lv<words.size();lv++) {
            for( int j=0;j< words.get(lv).wordName.length(); j++){
                System.out.println("***********");
                wordState.add('_');
            }
            StringBuilder wordBuilder = new StringBuilder();
            for( char c :wordState){
                wordBuilder.append(c).append(" ");
            }
            showWord=wordBuilder.toString();
            showWords.setHint(showWord);
            //System.out.println(showWord);
            int maxLength = words.get(lv).wordName.length();
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(maxLength);
            showWords.setFilters(filters);
            startBtn.setVisibility(View.GONE);
            compareBtn.setVisibility(View.VISIBLE);
            Timer=new CountDownTimer(50000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) { //This function operates the timer to display images on countdown
                    // Used for formatting digits to be in 2 digits only
                    timeleft= (millisUntilFinished / 1000) % 60;
                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    countdown.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    int resourceId = getResources().getIdentifier(words.get(lv).image1, "drawable", getPackageName());
                    //startBtn.setText("Guess Word");
                    hint1.setImageResource(resourceId);
                    if(f.format(sec).equals("40")){
                        int resourceId2 = getResources().getIdentifier(words.get(lv).image2, "drawable", getPackageName());
                        hint2.setImageResource(resourceId2);
                    }
                    if(f.format(sec).equals("30")){
                        int resourceId3 = getResources().getIdentifier(words.get(lv).image3, "drawable", getPackageName());
                        hint3.setImageResource(resourceId3);
                    }

                    if(f.format(sec).equals("20")){
                        int resourceId4 = getResources().getIdentifier(words.get(lv).image4, "drawable", getPackageName());
                        hint4.setImageResource(resourceId4);
                    }

                }

                @Override
                public void onFinish() {//When timer ends the game is ended and the player loses
                    // When the task is over it will print 00:00:00
                    countdown.setText("00:00:00");
                    EndGame(view);
                }
            }.start();

       // }  //End for loop
    }
    public void getWordData(){// This function gets the words and images name from there respected text files and stores in Word object
        String filepath = "words.txt";
        try {
            InputStream inputStream = getAssets().open("words.txt");
            InputStream inputStream2 = getAssets().open("images.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));

            String line,word,line2,name1=null,name2=null,name3=null,name4=null;
            while((line = reader.readLine())!=null){
                //words.add(line.trim());
                word=line.trim();
                //System.out.println(word);
                if((line2 = reader2.readLine())!=null){
                    name1=line2;
                   // System.out.println(name1);
                }
                if((line2 = reader2.readLine())!=null){
                    name2=line2;
                    //System.out.println(name2);
                }
                if((line2 = reader2.readLine())!=null){
                    name3=line2;
                    //System.out.println(name3);
                }
                if((line2 = reader2.readLine())!=null){
                    name4=line2;
                    //System.out.println(name4);
                }
                else if((line2 = reader2.readLine())==null){
                    //System.out.println("file finish");
                }
                Words w =new Words(word.toUpperCase(),count,name1,name2,name3,name4);
                words.add(w);
                count++;
                //showWords.setText(w.getWord());
            }
            reader.close();
            inputStream.close();
            reader2.close();
            inputStream2.close();


        } catch(FileNotFoundException e){
            System.out.println("File not Found");
        }
        catch(IOException e){
            System.out.println("Unexpected Error");
        }

        //System.out.println(words.size());
        System.out.println("--------------");
        System.out.println(words.get(2).wordName);
        System.out.println("--------------");

    }

    public void EndGame(View view) {
        Toast.makeText(this,"You have completed the Game Congrats Your total Score is :"+ points,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        // String s = getIntent().getStringExtra("username");
    }


    public void CompareWord(View view ) {
        String word1 =showWords.getText().toString().toUpperCase();
        String word2=words.get(lv).wordName;

        if(word1.equals(word2)){
            lv++;
            int resourceId = getResources().getIdentifier("clue", "drawable", getPackageName());
           hint1.setImageResource(resourceId);
           int resourceId2 = getResources().getIdentifier("clue", "drawable", getPackageName());
           hint2.setImageResource(resourceId2);
           int resourceId3 = getResources().getIdentifier("clue", "drawable", getPackageName());
           hint3.setImageResource(resourceId3);
           int resourceId4 = getResources().getIdentifier("clue", "drawable", getPackageName());
           hint4.setImageResource(resourceId4);
           showWords.setText("");
           wrongAttemp=3;
          // attempt.setText("Attempts left : "+wrongAttemp);
           startGame(view);
        }
        else{
            wrongAttemp--;
            attempt.setText("Attempts left : "+wrongAttemp);
            Toast.makeText(this,"Sorry Wrong Word ",Toast.LENGTH_SHORT).show();
            System.out.println(wrongAttemp);
        }
        if(wrongAttemp==0){
            EndGame(view);
        }
        //Note to self add warning when only on attempt left
        //add exit button;
    }
}