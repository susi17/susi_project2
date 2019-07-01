package com.example.orderkuy;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orderkuy.ServerSide.UserSession;
import com.example.orderkuy.activity.Login;
import com.example.orderkuy.fragment.HomeFragment;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //OnClickListeners
//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
//            }
//        });
//
//        mBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
//            }
//        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (new UserSession(getApplicationContext()).getIsLogin()){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.hitam));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            mCurrentPage = i;

            if (i == 0){

                mNextBtn.setEnabled(false);
                mBackBtn.setEnabled(false);
                mNextBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("");


            } else
            if (i == mDots.length - 1){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mNextBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("MULAI");
                mBackBtn.setText("");

            } else {

                mNextBtn.setEnabled(false);
                mBackBtn.setEnabled(false);
                mNextBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("");

            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
