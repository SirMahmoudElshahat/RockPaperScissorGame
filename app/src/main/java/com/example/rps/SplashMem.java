package com.example.rps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SplashMem extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    private SharedPreferences sp ;
    public static final String USER_NAME = "user name";
    public static final String MODE_KEY = "mode";
    public static final String EASY_MODE = "easy";
    public static final String NORMAL_MODE = "normal";
    public static final String HARD_MODE = "hard";

    private int[] images = {
            R.drawable.mem_w1,
            R.drawable.mem_w2,
            R.drawable.mem_w3,
            R.drawable.mem_w4,
            R.drawable.mem_w5,
            R.drawable.mem_w6,
            R.drawable.mem_w7
    };


    private String[] texts = {
            "عامل ايه",
            "",
            "خد قلبي",
            "بخخخ",
            "هكسبك",
            "يلا نلعب",
            "انا حزين دلوقتي"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_mem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.mem_imageView);
        textView = findViewById(R.id.mem_textView);

        Random random = new Random();
        int randomIndex = random.nextInt(images.length);

        imageView.setImageResource(images[randomIndex]);
        textView.setText(texts[randomIndex]);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getString(USER_NAME, null)!= null)
                {
                    Intent intent = new Intent(SplashMem.this, Home.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashMem.this, Setting.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1500);



    }
}