package com.project.goodwill.fragments;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.goodwill.R;
import com.project.goodwill.modules.VerticalViewPager;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class FindFragment extends Fragment {

    private static final String MY_NUM_KEY = "num";
    private int mNum;
    // Required empty public constructor
private View v;
    VerticalViewPager mPager;

    // You can modify the parameters to pass in whatever you want

    static final int NUMBER_OF_PAGES = 2;
    private ViewModelStore mViewModelStore;

    public void load(){

}


    private OnFragmentInteractionListener mListener;
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }
    public FindFragment() {
    }
    static FindFragment newInstance(int num) {
        FindFragment f = new FindFragment();
        Bundle args = new Bundle();
        args.putInt(MY_NUM_KEY, num);

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt(MY_NUM_KEY) : 0;
    }
    public View adapter(View view){
        MyAdapter mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager = view.findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);
        ImageView sobaka = view.findViewById(R.id.pet_photo);
        sobaka.setImageResource(mNum);


        return view;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_find, container, false);
        v = adapter(v);


        return v;
    }

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
    public void onStart(){
       super.onStart();
       try {
           mListener = (OnFragmentInteractionListener) getActivity();

       }catch (ClassCastException e){
            throw new RuntimeException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onStop(){
        super.onStop();


    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"RSUMED");

        v = adapter(v);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"Paused");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        public FindFragment getItem(int position) {

            switch (position) {
                case 0:
                    return newInstance(R.drawable.sobaka);
                case 1:
                    // return a different Fragment class here
                    // if you want want a completely different layout
                    return newInstance(R.drawable.five);
                default:
                    return null;
            }
        }
    }


}
