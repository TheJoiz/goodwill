package com.project.goodwill.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.goodwill.R;

import java.util.Locale;

import static com.firebase.ui.auth.AuthUI.TAG;


/**
 * Created by Sarun on 24-12-2015.
 */
public class SplashActivity extends Activity
{
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (!isOnline()) {
            Intent i = new Intent(getApplicationContext(), NointernetActivity.class);     // change the activity you want to load
            startActivity(i);
            SplashActivity.this.finish();
            finish();
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);  // Need full screen without title bars for splash screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uID = user.getUid();
                // No user is signed in
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference userRef = db.collection("users").document("user").collection("uID").document(uID);
                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Thread t = new Thread() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    public void run() {
                                        try {
                                            if(Locale.getDefault().getDisplayLanguage().equals("ru")){
                                                Locale locale = Locale.forLanguageTag("ua");
                                                Locale.setDefault(locale);
                                            }
                                            Thread.sleep(3000);  // change the time according to your needs(its in milliseconds)
                                            Intent i = new Intent(getApplicationContext(), PetActivity.class);     // change the activity you want to load
                                            startActivity(i);
                                            finish();

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                t.start();
                            } else {
                                Log.d(TAG, "No such document");
                                Thread t = new Thread() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    public void run() {
                                        try {
                                            if(Locale.getDefault().getDisplayLanguage().equals("ru")){
                                                Locale locale = Locale.forLanguageTag("ua");
                                                Locale.setDefault(locale);
                                            }
                                            Thread.sleep(3000);  // change the time according to your needs(its in milliseconds)
                                            Intent i = new Intent(getApplicationContext(), EnterActivity.class);     // change the activity you want to load
                                            startActivity(i);
                                            finish();

                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                t.start();

                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


            } else {
                Thread t = new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void run() {
                        try {

                            Thread.sleep(3000);  // change the time according to your needs(its in milliseconds)
                            Intent i = new Intent(getApplicationContext(), EnterActivity.class);     // change the activity you want to load
                            startActivity(i);
                            finish();

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        }
    }
}