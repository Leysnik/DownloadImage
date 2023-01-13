package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    LinearLayout imageRow;
    Button loadButton;

    String [] imageAddresses = {"https://www.fonstola.ru/pic/202003/1920x1080/fonstola.ru_376107.jpg",
            "https://i.ytimg.com/vi/4PUhM01PZf8/maxresdefault.jpg",
            "https://i.etsystatic.com/24419106/r/il/e571b0/2440722344/il_1588xN.2440722344_gi2c.jpg"};
    Data data;
    int count = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRow = findViewById(R.id.img_row);
        loadButton = findViewById(R.id.load_btn);
    }

    public void loadIMG(View view) {
        if (count<2){
            count++;
            data = new Data.Builder().putString("Address", imageAddresses[count]).build();
            //Создание экземпляра потока
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(IMGLoader.class).setInputData(data).build();
            //Создание менеджера потоков и запуск
            WorkManager workManager = WorkManager.getInstance();
            workManager.enqueue(workRequest);
            workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(@androidx.annotation.Nullable WorkInfo workInfo) {
                    String imgText = Objects.requireNonNull(workInfo).getOutputData().getString("image");
                    Bitmap img = IMGToken.jsonToIMG(imgText);
                    ((ImageView) imageRow.getChildAt(count)).setImageBitmap(img);


                }
            });
        }
    }
}