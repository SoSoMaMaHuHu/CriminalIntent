package com.example.administrator.criminalintent_github;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity
        implements View.OnClickListener , CrimeFragment.Callbacks{
    private static final String EXTRA_CRIME_ID = "com.example.administrator.criminalintent_github.crime_id";

    private ViewPager mViewPager;
    private Button JumpFirst;
    private Button JumpLast;
    private List<Crime> mCrimes;

    @Override
    public void onCrimeUpdated(Crime crime) {

    }

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        //点击的Crime的ID
        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);
        JumpFirst = (Button)findViewById(R.id.first);
        JumpLast = (Button)findViewById(R.id.last);

        JumpFirst.setOnClickListener(this);
        JumpLast.setOnClickListener(this);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Log.d("FMStatePagerAdapter","position "+position);
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for(int i=0;i<mCrimes.size(); i++){
            if (mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                if (i==0){
                    JumpFirst.setEnabled(false);
                }

                if (i==mCrimes.size()-1){
                    JumpLast.setEnabled(false);
                }
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    JumpFirst.setEnabled(false);
                }else{
                    JumpFirst.setEnabled(true);
                }

                if(position == mCrimes.size()-1){
                    JumpLast.setEnabled(false);
                }else{
                    JumpLast.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.first){
            mViewPager.setCurrentItem(0,false);
        }else if(v.getId()==R.id.last){
            mViewPager.setCurrentItem(mCrimes.size()-1, false);
        }
    }

}
