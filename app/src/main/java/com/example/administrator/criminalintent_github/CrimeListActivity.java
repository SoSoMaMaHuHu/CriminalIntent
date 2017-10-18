package com.example.administrator.criminalintent_github;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeListActivity";
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
