package com.example.downloadimage;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class IMGToken {
    public static String imageToJson(Bitmap img){
        Gson gson = new Gson();
        return gson.toJson(img);
    }
    public static Bitmap jsonToIMG(String img){
        Gson gson = new Gson();
        return gson.fromJson(img,Bitmap.class);
    }
}
