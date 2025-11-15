package com.example.wordgame;

import android.app.Application;

import java.io.Serializable;


public class Words extends Application implements Serializable{
final String wordName;
final int wordId;
final String image1;
final String image2;
final String image3;
final String image4;
public Words(String word, int id, String hint1,String hint2,String hint3,String hint4){
    this.wordName = word;
    this.wordId = id;
    this.image1 = hint1;
    this.image2 = hint2;
    this.image3 = hint3;
    this.image4 = hint4;

}
    public String getWord() {
        return wordName;
    }

    public int getwordId() {
        return wordId;
    }
    public String getImage1() {
        return image1;
    }
    public String getImage2() {
        return image2;
    }
    public String getImage3() {
        return image3;
    }
    public String getImage4() {
        return image4;
    }

}
