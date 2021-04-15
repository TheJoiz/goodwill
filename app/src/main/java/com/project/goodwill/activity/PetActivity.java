package com.project.goodwill.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPagerUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.goodwill.fragments.AboutFragment;
import com.project.goodwill.modules.DbClass;
import com.project.goodwill.fragments.DonateFragment;
import com.project.goodwill.modules.ObjectSerializer;
import com.project.goodwill.modules.Product;
import com.project.goodwill.fragments.ProfileFragment;
import com.project.goodwill.R;
import com.project.goodwill.fragments.ShopFragment;
import com.project.goodwill.modules.VerticalViewPager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;


public class PetActivity extends AppCompatActivity  implements ProfileFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, ShopFragment.OnFragmentInteractionListener, DonateFragment.OnFragmentInteractionListener {
    private static final String TAG = "sdadasd";
    private Fragment fragmentOne = new FragmentOne();
    public static ArrayList<Product> products = new ArrayList<Product>();
    private static double[] hearts = new double[15];
    private int likeCost = DbClass.getLikeCost();
    private View currView;
    private ViewPager viewPager;
    private static SharedPreferences sharedPref ;
    public static String lng;

    public static void clearcart(){
        products = new ArrayList<>();
        hearts = new double[15];
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
    public void next(View v){
        Intent i = new Intent(getApplicationContext(),InstructionActivity.class);
        startActivity(i);
        finish();
    }
    public void fillHeart(){
           // pct goes from 0 to 100
        int pos = viewPager.getCurrentItem();
        View view = ViewPagerUtils.getCurrentView(viewPager);
        View heart = view.findViewById(R.id.heart);
        heart.getBackground().setLevel((int)hearts[pos]);
        Log.d("position",Integer.toString(pos));
        for (double i: hearts
             ) {
            Log.d("mas",Double.toString(i));
        }
        if (hearts[pos] > 0){
            ImageView imageView = view.findViewById(R.id.pet_photo);
            switch (pos){
                case 0:
                    imageView.setImageResource(R.drawable.mks12);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.b2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.l6);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.kp8);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.kap18);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.ts21);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.tk20);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.mkk10);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.sks14);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.k4);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.sks14);
                    break;
                case 11:
                    imageView.setImageResource(R.drawable.sks14);
                    break;
                case 12:
                    imageView.setImageResource(R.drawable.k4);
                    break;
                case 13:
                    imageView.setImageResource(R.drawable.k4);
                    break;
                case 14:
                    imageView.setImageResource(R.drawable.v16);
                    break;
            }
        }

    }
    public void closeDescr(View v){
        currView.findViewById(R.id.sb_descr).setVisibility(View.INVISIBLE);
        currView.findViewById(R.id.closeDescr).setVisibility(View.INVISIBLE);
    }
    public void showDescr(View v){
        currView = ViewPagerUtils.getCurrentView((ViewPager) findViewById(R.id.viewpager));
        currView.findViewById(R.id.sb_descr).setVisibility(View.VISIBLE);
        currView.findViewById(R.id.closeDescr).setVisibility(View.VISIBLE);

    }
    public void welc(View v){
        Intent i = new Intent(getApplicationContext(),EnterActivity.class);
        startActivity(i);
        finish();
    }
    static final int NUMBER_OF_PAGES = 10;
    MyAdapter mAdapter;
    VerticalViewPager mPager;
    private  void seHeart(){
        View view = ViewPagerUtils.getCurrentView(viewPager);
        View heart = view.findViewById(R.id.heart);
        TextView textView =(TextView)view.findViewById(R.id.price);
        double prc = Double.parseDouble(textView.getText().toString());
        int pos = viewPager.getCurrentItem();
        int curr = heart.getBackground().getLevel();
        hearts[pos] = curr + (10000 / (prc / DbClass.getLikeCost()));

    }
    public void addProduct(View v){
        View view = ViewPagerUtils.getCurrentView((ViewPager) findViewById(R.id.viewpagershop));
        TextView textView = view.findViewById(R.id.sb_descr);
        Button button = view.findViewById(R.id.button9);
        button.setClickable(false);
        String str = textView.getText().toString();
        seHeart();
        fillHeart();

        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(hearts);

        editor.putString("hearts", json);


        if(!products.isEmpty()) {
            Iterator<Product> it = products.iterator();
            while (it.hasNext()){
                Product p = it.next();
                if (p.getName().equals(str)) {
                    int qtt = p.getCount();
                    p.setCount(qtt + 1);
                    break;
                }
                if(!it.hasNext()){
                    products.add(new Product(str, 1));
                }
            }
        } else {
            products.add(new Product(str, 1));
        }
        button.setClickable(true);
        try {
            editor.putString("cart", ObjectSerializer.serialize(products));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
        if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("uk")){

            Toast.makeText(getApplicationContext(), "Товар у кошику", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(),"Item in cart",Toast.LENGTH_SHORT).show();
        }

    }
    public void goToCart(View v){
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                i.putExtra("products",products);
                i.putExtra("likecost",likeCost);
                startActivity(i);
                finish();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            View v = findViewById(R.id.viewpager);
            final View b = findViewById(R.id.viewpagershop);
            switch (item.getItemId()) {
                case R.id.navigation_donate:
                    v.setClickable(false);
                    b.setClickable(false);
                    v.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                    loadFragment(new DonateFragment());

                    return true;
                case R.id.navigation_profile:
                    v.setClickable(false);
                    b.setClickable(false);
                    v.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                    item.setEnabled(true);
//                    mPager.setAdapter(null);
                    loadFragment(new ProfileFragment());
                    Log.d(TAG,"jopa");
                    return true;
                case R.id.navigation_shop:
                    ShopFragment.MyAdapter Adapter = new ShopFragment.MyAdapter(getSupportFragmentManager());
                    mPager = (VerticalViewPager) b;
                    Field mFlingDistance;

                    mPager.setAdapter(Adapter);
                    loadFragment(new ShopFragment());
                    v.setClickable(false);
                    b.setClickable(true);
                    b.setVisibility(View.VISIBLE);
                    v.setVisibility(View.INVISIBLE);
                    viewPager = (ViewPager) b;
                    try {
                        mFlingDistance =  ViewPager.class.getDeclaredField("mFlingDistance");
                        mFlingDistance.setAccessible(true);
                        mFlingDistance.set(mPager, 10000);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    fillHeart();
                    return true;
                case R.id.navigation_about:
                    v.setClickable(false);
                    b.setClickable(false);
                    v.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(getApplicationContext(), InfoActivity.class);     // change the activity you want to load
                    startActivity(i);
                    finish();
                    return true;
                case R.id.navigation_find:

//                    fragment = new FindFragment();
//                    loadFragment(fragment);
//                   ((FindFragment) fragment).load();
                    mAdapter = new MyAdapter(getSupportFragmentManager());
                    mPager = (VerticalViewPager) v;
                    mPager.setAdapter(mAdapter);
                    try {
                        mFlingDistance =  ViewPager.class.getDeclaredField("mFlingDistance");
                        mFlingDistance.setAccessible(true);
                        mFlingDistance.set(mPager, 10000);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    loadFragment(fragmentOne);
                    v.setClickable(true);
                    b.setClickable(false);
                    b.setVisibility(View.INVISIBLE);
                    v.setVisibility(View.VISIBLE);

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        lng = getResources().getConfiguration().locale.getLanguage();
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            products = (ArrayList) extras.get("products");
//        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Need full screen without title bars for splash screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pet);
        View botom = findViewById(R.id.navigation);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(hearts.length < 15){
            Arrays.fill(hearts,0.0);
        }
        Gson gson = new Gson();
        String hrt = sharedPref.getString("hearts", "");
        String crt = sharedPref.getString("cart", "");

        if(!hrt.equals("")) {
            hearts = gson.fromJson(hrt, double[].class);
        }
        if(!crt.equals("")){
            try {
                products = (ArrayList<Product>) ObjectSerializer.deserialize(crt);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        viewPager = (ViewPager) findViewById(R.id.viewpagershop);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) botom;
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                fillHeart();
            }
        });

        Fragment fragment = fragmentOne;

        loadFragment(fragment);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        BottomNavigationView navigation = (BottomNavigationView) botom;
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.get("shop") == "shop") {
                View v = findViewById(R.id.viewpager);
                View b = findViewById(R.id.viewpagershop);
                ShopFragment.MyAdapter Adapter = new ShopFragment.MyAdapter(getSupportFragmentManager());
                mPager = (VerticalViewPager) b;
                mPager.setAdapter(Adapter);
                loadFragment(new ShopFragment());
                viewPager = (ViewPager) b;
                b.setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);
            }
        }
//        ImageView close = findViewById(R.id.closeDescr);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeDescr(v);
//            }
//        });

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        currView.findViewById(R.id.sb_descr).setVisibility(View.INVISIBLE);
    }
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                    case 0:
                        return FragmentOne.newInstance(R.drawable.sb_1,R.string.sb1);
                    case 1:
                        return FragmentOne.newInstance(R.drawable.sb_2,R.string.sb2);
                    case 2:
                        return FragmentOne.newInstance(R.drawable.sb_3,R.string.sb3);
                case 3:
                    return FragmentOne.newInstance(R.drawable.sb_4,R.string.sb4);
                case 4:
                    return FragmentOne.newInstance(R.drawable.sb_5,R.string.sb5);
                case 5:
                    return FragmentOne.newInstance(R.drawable.sb_6,R.string.sb6);
                case 6:
                    return FragmentOne.newInstance(R.drawable.sb_7,R.string.sb7);
                case 7:
                    return FragmentOne.newInstance(R.drawable.sb_8,R.string.sb8);
                case 8:
                    return FragmentOne.newInstance(R.drawable.sb_9,R.string.sb9);
                case 9:
                    return FragmentOne.newInstance(R.drawable.sb_10,R.string.sb10);
                default:
                    return null;
            }
        }
    }
    public static class FragmentOne extends Fragment {

        private static final String MY_NUM_KEY = "num";
        private static final String MY_STR_KEY = "str";

        private int mNum;
        private int mStr;

        // You can modify the parameters to pass in whatever you want
        static FragmentOne newInstance(int num,int str) {
            FragmentOne f = new FragmentOne();
            Bundle args = new Bundle();
            args.putInt(MY_NUM_KEY, num);
            args.putInt(MY_STR_KEY, str);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mNum = getArguments() != null ? getArguments().getInt(MY_NUM_KEY) : 0;
            mStr = getArguments() != null ? getArguments().getInt(MY_STR_KEY) : R.string.nf;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_find, container, false);
            ImageView sobaka = v.findViewById(R.id.pet_photo);
            TextView sb_descr = v.findViewById(R.id.sb_descr);
            sb_descr.setText(mStr);
            sobaka.setImageResource(mNum);

            return v;
        }
    }
}


