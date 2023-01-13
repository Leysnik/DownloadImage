package com.example.downloadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.URL;

public class IMGLoader extends Worker {
    Data outputData;
    public IMGLoader( @androidx.annotation.NonNull Context context, @androidx.annotation.NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @androidx.annotation.NonNull
    @Override
    public Result doWork() {
        String address = getInputData().getString("Address");
        try {
            URL imgAddress = new URL(address);
            Bitmap img = BitmapFactory.decodeStream(imgAddress.openStream());
            outputData = new Data.Builder().putString("image",IMGToken.imageToJson(img)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(outputData);
    }
}
