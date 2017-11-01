package com.example.administrator.criminalintent_github;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CrimeListFragment extends Fragment {
    private static final int REQUEST_CRIME = 1;
    private static final String TAG = "CrimeListFragment";
    private int dbgcount=0;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private UUID crimeChangId;
    private int mposition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "222222"+getActivity().toString()+container.toString());
        // 加载recyclerView布局，并设置其布局管理器，最后设置adapter并加载数据显示出来。
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 返回recyclerview时，数据可能已更改，所以recyclerview显示也要更新。
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CRIME){
            if (data!=null){
                mposition = CrimeFragment.getCrimeIndexFromIntent(data);
            }
        }
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            //mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemChanged(mposition);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;


        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView)itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText((String)DateFormat.format("E yyyy.MM.dd, kk:mm",mCrime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {

            //将ID作为参数，存在Intent中，在新的Activity中再Intent中的ID，使得其知道点击了哪个Crime。
            crimeChangId = mCrime.getId();
            Intent intent = CrimePagerActivity.newIntent(getActivity(), crimeChangId);
            //当返回时，我们可以在onResume方法中进行UI的更新。
            startActivityForResult(intent,REQUEST_CRIME);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        // 创建ViewHolder
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //parent: android.support.v7.widget.RecyclerView
            Log.d(TAG, "onCreateViewHolder "+parent.toString()+" dbgcount: "+dbgcount++);
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        //将数据与ViewHolder绑定
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder dbgcount: "+dbgcount++);
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCout:"+mCrimes.size()+" dbgcount: "+dbgcount++);
            return mCrimes.size();
        }
    }
}
