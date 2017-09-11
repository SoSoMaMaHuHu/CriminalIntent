package com.example.administrator.criminalintent_github;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
