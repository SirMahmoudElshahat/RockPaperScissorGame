package com.example.rps;

import static com.example.rps.SplashMem.EASY_MODE;
import static com.example.rps.SplashMem.HARD_MODE;
import static com.example.rps.SplashMem.MODE_KEY;
import static com.example.rps.SplashMem.NORMAL_MODE;
import static com.example.rps.SplashMem.USER_NAME;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class Home extends AppCompatActivity {
    private ImageButton paper;
    private  ImageButton rock;
    private ImageButton scissors;
    private  ImageView phoneChoose;
    private ImageButton setting;
    private TextView phoneResult;
    private int countPhoneResult = 0;
    private TextView userResult;
    private int countUserResult = 0;
    private  TextView animate;
    private SharedPreferences sp ;

    private int[] images = {
            R.drawable.r,
            R.drawable.p,
            R.drawable.s,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rock = findViewById(R.id.rockButton);
        paper = findViewById(R.id.paperButton);
        scissors = findViewById(R.id.scissorsButton);
        phoneChoose = findViewById(R.id.phoneChooseImageView);
        setting = findViewById(R.id.settingImageButton);
        phoneResult = findViewById(R.id.phoneCountTextView);
        userResult = findViewById(R.id.userCountTextView);
        animate = findViewById(R.id.animateTextView);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        userResult.setText(sp.getString(USER_NAME, null)+": 0");

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtons();
                rock.setSelected(true);
                playGame(0);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtons();
                paper.setSelected(true);
                playGame(1);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButtons();
                scissors.setSelected(true);
                playGame(2);
            }
        });

    }
    private void resetButtons() {
        paper.setSelected(false);
        rock.setSelected(false);
        scissors.setSelected(false);
    }
    private void playGame(int userChoice) {
        animate.setText("⏳");

        ValueAnimator animator = ValueAnimator.ofFloat(0, 2);
        animator.setDuration(2000);
        animator.setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            int index = Math.round(animatedValue);
            phoneChoose.setImageResource(images[index]);
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int getphoneChoice = generatePhoneChoose(userChoice);
                phoneChoose.setImageResource(images[getphoneChoice]);
                determineResult(userChoice,getphoneChoice);
            }
        });

        animator.start();
    }
    private int generatePhoneChoose(int userChoice){
        Random random = new Random();
        String modeg = sp.getString(MODE_KEY,null);
        switch (modeg) {
            case EASY_MODE:
                switch (userChoice){
                    case 0:
                        return 2;
                    case 1:
                        return 0;
                    case 2:
                        return 1;
                }
            case HARD_MODE:
                switch (userChoice){
                    case 0:
                        return 1;
                    case 1:
                        return 2;
                    case 2:
                        return 0;
                }
            case NORMAL_MODE:
            default:
                return random.nextInt(images.length);
        }
    }
    private void determineResult(int userChoise,int phoneChoise){
        String modeR = sp.getString(MODE_KEY,null);
        switch (modeR) {
            case EASY_MODE:
                countUserResult++;
                userResult.setText(sp.getString(USER_NAME, null)+": " +countUserResult);
                showResult("You Win");
                break;
            case HARD_MODE:
                countPhoneResult++;
                phoneResult.setText("Phone: "+countPhoneResult);
                showResult("Phone Win");
                break;
            case NORMAL_MODE:
                normalResult(userChoise,phoneChoise);
                break;
        }
    }
    private void normalResult(int userChoice, int phoneChoice) {
        if (userChoice == phoneChoice) {
            showResult("Draw");
        } else {
            if ((userChoice == 0 && phoneChoice == 2) ||
                    (userChoice == 1 && phoneChoice == 0) ||
                    (userChoice == 2 && phoneChoice == 1)) {
                countUserResult++;
                userResult.setText(sp.getString(USER_NAME, null)+": " +countUserResult);
                showResult("You Win");
            } else {
                countPhoneResult++;
                phoneResult.setText("Phone: "+countPhoneResult);
                showResult("Phone Win");
            }
        }
    }

    private void showResult(String result){
        rock.setEnabled(false);
        paper.setEnabled(false);
        scissors.setEnabled(false);
        animate.setText(result);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                rock.setEnabled(true);
                paper.setEnabled(true);
                scissors.setEnabled(true);
                animate.setText("");
                resetButtons();
            }
        }, 3000);


    }


}