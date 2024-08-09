package com.example.rps;

import static com.example.rps.SplashMem.EASY_MODE;
import static com.example.rps.SplashMem.HARD_MODE;
import static com.example.rps.SplashMem.MODE_KEY;
import static com.example.rps.SplashMem.NORMAL_MODE;
import static com.example.rps.SplashMem.USER_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Setting extends AppCompatActivity {

    private EditText user_name;
    private ImageButton easy;
    private ImageButton normal;
    private ImageButton hard;
    private Button play;
    private SharedPreferences sp ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user_name = findViewById(R.id.editText_userName);
        easy = findViewById(R.id.easy);
        normal = findViewById(R.id.normal);
        hard = findViewById(R.id.hard);
        play =findViewById(R.id.play);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUserName = sp.getString(USER_NAME, null);
        String savedMode = sp.getString(MODE_KEY, null);

        if(savedUserName != null){
            user_name.setText(savedUserName);
        }
        if (savedMode != null) {
            switch (savedMode) {
                case EASY_MODE:
                    easy.setSelected(true);
                    break;
                case NORMAL_MODE:
                    normal.setSelected(true);
                    break;
                case HARD_MODE:
                    hard.setSelected(true);
                    break;
                default:
                    reset();
                    break;
            }
        } else {
            reset();
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUserName = user_name.getText().toString();
                if (!enteredUserName.isEmpty() && sp.getString(MODE_KEY, null) != null) {
                    saveUserName(enteredUserName);
                    Intent intent = new Intent(Setting.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Setting.this, "Please enter your name or chosse mode", Toast.LENGTH_SHORT).show();
                }
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                easy.setSelected(true);
                saveMode(EASY_MODE);
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                normal.setSelected(true);
                saveMode(NORMAL_MODE);
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                hard.setSelected(true);
                saveMode(HARD_MODE);
            }
        });

    }
    private void reset(){
        easy.setSelected(false);
        normal.setSelected(false);
        hard.setSelected(false);
    }
    private void saveMode(String mode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MODE_KEY, mode);
        editor.apply();
    }
    private void saveUserName(String userName) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }
}