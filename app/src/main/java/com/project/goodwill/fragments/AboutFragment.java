package com.project.goodwill.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.goodwill.modules.PrefManager;
import com.project.goodwill.R;

public class AboutFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ViewPager viewPager;
//    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private PrefManager prefManager;
    public AboutFragment() {
        // Required empty public constructor
    }
//    public void createSlider() {
//        prefManager = new PrefManager(this.getContext());
//        if (!prefManager.isFirstTimeLaunch()) {
//
//        }
//
//        // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//
//        getActivity().setContentView(R.layout.fragment_about);
//
//        viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager_about);
//        dotsLayout = (LinearLayout) getActivity().findViewById(R.id.layoutDots_about);
//
//
//        // layouts of all welcome sliders
//        // add few more layouts if you want
//        layouts = new int[]{
//                R.layout.welcome_slide1,
//                R.layout.welcome_slide2,
//                R.layout.welcome_slide3,
//                R.layout.welcome_slide4};
//
//        // adding bottom dots
//        addBottomDots(0);
//
//        // making notification bar transparent
//        changeStatusBarColor();
//
//        myViewPagerAdapter = new MyViewPagerAdapter();
//        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//
//    }
//
//    private void addBottomDots(int currentPage) {
//        dots = new TextView[layouts.length];
//
//        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
//
//        dotsLayout.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(this.getContext());
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(35);
//            dots[i].setTextColor(colorsInactive[currentPage]);
//            dotsLayout.addView(dots[i]);
//        }
//
//        if (dots.length > 0)
//            dots[currentPage].setTextColor(colorsActive[currentPage]);
//    }

//    private int getItem(int i) {
//        return viewPager.getCurrentItem() + i;
//    }


    //  viewpager change listener
//    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//
//        @Override
//        public void onPageSelected(int position) {
//            if (position <= 2) {
//                addBottomDots(position);
//            } else {
//                dotsLayout.removeAllViews();
//            }
//            // changing the next button text 'NEXT' / 'GOT IT'
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//
//        }
//    };

    /**
     * Making notification bar transparent
     */
//    private void changeStatusBarColor() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getActivity().getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }

    /**
     * View pager adapter
     */
//    public class MyViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//
//        public MyViewPagerAdapter() {
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = layoutInflater.inflate(layouts[position], container, false);
//            container.addView(view);
//
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return layouts.length;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object obj) {
//            return view == obj;
//        }
//
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            View view = (View) object;
//            container.removeView(view);
//        }
//    }

    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
}
