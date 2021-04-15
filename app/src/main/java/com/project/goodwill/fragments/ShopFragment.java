package com.project.goodwill.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.goodwill.DoubleClickListener;
import com.project.goodwill.R;
import com.project.goodwill.modules.VerticalViewPager;
import com.project.goodwill.activity.PetActivity;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

import static com.project.goodwill.modules.DbClass.getName;

public class ShopFragment extends Fragment {
    static final int NUMBER_OF_PAGES = 15;
    MyAdapter mAdapter;
    VerticalViewPager mPager;
    public View v;
    private static ArrayList lister = getName();
    private static final String MY_NUM_KEY = "num";
    private static final String MY_STR_KEY = "str";
    private static final String MY_PRC_KEY = "prc";
    private static final String MY_WGT_KEY = "wgt";
    public GifImageView gifDrawable;
    public ImageView sobaka;
    private int mNum;

    private String mStr;
    private double mPrc;
    private double mWgt;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
        // Required empty public constructor
    }
    public View getV() {
        return v;
    }

    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(int num, String str, double prc,double wgt) {
        ShopFragment f = new ShopFragment();
        Bundle args = new Bundle();
        args.putInt(MY_NUM_KEY, num);
        args.putString(MY_STR_KEY, str);
        args.putDouble(MY_PRC_KEY, prc);
        args.putDouble(MY_WGT_KEY, wgt);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Language", PetActivity.lng);
        mNum = getArguments() != null ? getArguments().getInt(MY_NUM_KEY) : 0;
        mStr = getArguments() != null ? getArguments().getString(MY_STR_KEY) : "Not Found";
        mPrc = getArguments() != null ? getArguments().getDouble(MY_PRC_KEY) : 0;
        mWgt= getArguments() != null ? getArguments().getDouble(MY_WGT_KEY) : 0;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_shop, container, false);

            gifDrawable = v.findViewById(R.id.gifAnim);

        sobaka = v.findViewById(R.id.pet_photo);
        TextView sb_descr = v.findViewById(R.id.sb_descr);
        TextView price = v.findViewById(R.id.price);
        TextView weight = v.findViewById(R.id.weight);
        sobaka.setOnClickListener(new

                                           DoubleClickListener() {

                                               @Override
                                               public void onDoubleClick (){
                                                   final GifImageView gifDrawable = v.findViewById(R.id.gifAnim);
                                                   gifDrawable.setImageResource(R.drawable.heart_yellow_gif);
                                                   gifDrawable.invalidate();
                                                   Handler handler = new Handler();
                                                   handler.postDelayed(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           gifDrawable.setImageResource(android.R.color.transparent);
                                                           v.findViewById(R.id.imageView25).callOnClick();
                                                       }
                                                   }, 800);

                                                   // double-click code that is executed if the user double-taps
                                                   // within a span of 200ms (default).
                                               }
                                           });
        sb_descr.setText(mStr);
        sobaka.setImageResource(mNum);
        price.setText(Double.toString(mPrc));
        weight.setText(Double.toString(mWgt));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onFragmentInteraction(Uri uri);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private String TAG = "adapter";

//        private static String[] mas = getName();
//        private static double[] prc = getPrice();
//        private static double[] wgt = getWeigth();
        private static String[] mas = (String[]) lister.get(0);
        private static double[] prc = (double[]) lister.get(1);
        private static double[] wgt = (double[]) lister.get(2);

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }

        @Override
        public Fragment getItem(int position) {

            String items[] = mas;
            if (position == 0 || position == 1) {
                if(PetActivity.lng.equals("ru") || PetActivity.lng.equals("uk")) {
                    items[0] = "М'який корм Клуб 4 лапи Ніжне меню для цуценят";
                    items[1] = "Дерев'яний будиночок для собаки";
                } else{
                    items[0] = "Soft food Club 4 paws Delicate menu for puppies";
                    items[1] = "Wooden dog house";
                }
                prc[0] = 8.55;
                prc[1] = 600;
                wgt[0] = 100;
                wgt[1] = 1;

            }
            switch (position) {
                case 0:
                    return ShopFragment.newInstance(R.drawable.mks11, items[0], prc[0],wgt[0]);
                case 1:
                    return ShopFragment.newInstance(R.drawable.b1, items[1], prc[1],wgt[1]);
                case 2:
                    Log.d(TAG, items[1]);
                    return ShopFragment.newInstance(R.drawable.l5, items[2], prc[2],wgt[2]);
                case 3:
                    return ShopFragment.newInstance(R.drawable.kp7, items[3], prc[3],wgt[3]);
                case 4:
                    return ShopFragment.newInstance(R.drawable.kap17, items[4], prc[4],wgt[4]);
                case 5:
                    return ShopFragment.newInstance(R.drawable.ts21, items[5], prc[5],wgt[5]);
                case 6:
                    return ShopFragment.newInstance(R.drawable.tk19, items[6], prc[6],wgt[6]);
                case 7:
                    return ShopFragment.newInstance(R.drawable.mkk9, items[7], prc[7],wgt[7]);
                case 8:
                    return ShopFragment.newInstance(R.drawable.sks13, items[8], prc[8],wgt[8]);
                case 9:
                    return ShopFragment.newInstance(R.drawable.k3, items[9], prc[9],wgt[9]);
                case 10:
                    return ShopFragment.newInstance(R.drawable.sks13, items[10], prc[10],wgt[10]);
                case 11:
                    return ShopFragment.newInstance(R.drawable.sks13, items[11], prc[11],wgt[11]);
                case 12:
                    return ShopFragment.newInstance(R.drawable.k3, items[12], prc[12],wgt[12]);
                case 13:
                    return ShopFragment.newInstance(R.drawable.k3, items[13], prc[13],wgt[13]);
                case 14:
                    return ShopFragment.newInstance(R.drawable.v15, items[14], prc[14],wgt[14]);
                default:
                    return null;
            }

        }




//        private static double[] getPrice() {
//            final double[] price = new double[15];
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("products")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        private static final String TAG = "sssss";
//
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//
//                                int i = 0;
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    price[i] = Double.valueOf(String.valueOf(document.getData().get("price")));
//                                    i++;
//                                }
//                            } else {
//                                Log.w(TAG, "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//
//            return price;
//        }

//        private static double[] getWeigth(){
//            final double[] weight = new double[15];
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("products")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        private static final String TAG = "sssss";
//
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//
//                                int i = 0;
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    weight[i] = Double.valueOf(String.valueOf(document.getData().get("weight")));
//                                    i++;
//                                }
//                            } else {
//                                Log.w(TAG, "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//
//            return weight;
//        }

    }


}
