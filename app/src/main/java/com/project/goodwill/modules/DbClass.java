package com.project.goodwill.modules;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.goodwill.activity.PetActivity;

import java.util.ArrayList;

public class DbClass {
  private static  int likeCost = 0;
    public static int getLikeCost(){


        DocumentReference docRef;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (user != null) {
            String uId = user.getUid();
            docRef = db.collection("users").document("user").collection("uID").document(uId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            likeCost =(int) (long) document.get("like_cost");
                        }
                    }
                }
            });
        }
        return likeCost ;
    }
    public static ArrayList getName() {
        ArrayList list = new ArrayList();
        final String[] str = new String[15];
        final double[] price = new double[15];
        final double[] weight = new double[15];
        if (PetActivity.lng.equals("ru") || PetActivity.lng.equals("uk")) {


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("products")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        private static final String TAG = "sssss";

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                int i = 0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    str[i] = (String) document.getData().get("name");
                                    price[i] = Double.valueOf(String.valueOf(document.getData().get("price")));
                                    weight[i] = Double.valueOf(String.valueOf(document.getData().get("weight")));
                                    i++;
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });


        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("products")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        private static final String TAG = "sssss";

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                int i = 0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    str[i] = (String) document.getData().get("name_en");
                                    price[i] = Double.valueOf(String.valueOf(document.getData().get("price")));
                                    weight[i] = Double.valueOf(String.valueOf(document.getData().get("weight")));
                                    i++;
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        list.add(str);
        list.add(price);
        list.add(weight);
        return list;
    }
}
