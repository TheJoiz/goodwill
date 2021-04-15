package com.project.goodwill.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.goodwill.R;

import java.util.HashMap;
import java.util.Map;

public class YourlikeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseFirestore db;


    private static final String TAG = "Like Cost = ";
    public void set_like(View v){
        Log.d(TAG,"Setting like");
        Button b = (Button) v.findViewById(v.getId());
        String text = b.getText().toString();
        String cost_t =text.substring(0,2);
        int cost = Integer.parseInt(cost_t);
        if(cost == 10){
            cost = 100;
        }
        Log.d(TAG,""+cost);
        switch (cost_t){
            case "20":
                Log.d(TAG,"20");
                break;
            case "40":
                Log.d(TAG,"40");
                break;
            case "60":
                Log.d(TAG,"60");
                break;
        }
        if(user != null){
            String uId = user.getUid();
            Map<String,Object> data = new HashMap<>();
            data.put("userRole","user");
            data.put("like_cost",cost);
            db.collection("users").document("user").collection("uID").document(uId).set(data);
        }
        Intent i = new Intent(getApplicationContext(), PhotoActivity.class);     // change the activity you want to load
        startActivity(i);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_yourlike);
    }

}
