package com.project.goodwill.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.goodwill.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
//            finish();
//        }
//        else {
        if(Locale.getDefault().getLanguage().equals("ru")){
            Locale myLocale = new Locale("uk");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
            requestWindowFeature(Window.FEATURE_NO_TITLE);  // Need full screen without title bars for splash screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build());

// Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.splash)
                            .setTheme(R.style.LoginTheme)
                            .setTosAndPrivacyPolicyUrls(
                                    "https://docs.wixstatic.com/ugd/c2b153_bb60028e86d34222a118e2f6dde2877b.pdf",
                                    "https://docs.wixstatic.com/ugd/c2b153_aec22ec6b95148b4bedbf2cf929db476.pdfl")
                            .build(),
                    RC_SIGN_IN);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currUser != null;
                String uId = currUser.getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> map = new HashMap<>();
                map.put("userRole","user");
                db.collection("users").document("user").collection("uID").document(uId).set(map);


                Intent i = new Intent(getApplicationContext(),YourlikeActivity.class);     // change the activity you want to load
                startActivity(i);
                finish();
                // ...
            } else {

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);     // change the activity you want to load
//                        startActivity(i);
//                        finish();
//                    }
//                });
    }

}
