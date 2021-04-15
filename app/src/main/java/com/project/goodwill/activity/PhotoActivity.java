package com.project.goodwill.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.project.goodwill.R;

public class PhotoActivity extends AppCompatActivity {
    public void next(View v){
        Intent i = new Intent(getApplicationContext(),PetActivity.class);
        startActivity(i);
        finish();
    }
    public void shop(View v){
        Intent i = new Intent(getApplicationContext(),PetActivity.class);
        i.putExtra("shop","shop");
        startActivity(i);
        finish();
    }
    public void about(View v){
        Intent i = new Intent(getApplicationContext(),InfoActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Need full screen without title bars for splash screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_photo);

    }
}
