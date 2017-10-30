package com.example.administrator.criminalintent_github;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CrimeFragment extends Fragment{

    private static final String ARG_CRIME_ID = "crime_id";

    private Crime mCrime;
    private CrimeLab mCrimeLab;
    private EditText mTitleField;
    private Button mDataButton;
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static int getCrimeIndex(Intent intent){
        return intent.getIntExtra(ARG_CRIME_ID,0);
    }
    public void returnResult(){
        Intent data = new Intent();

        mCrimeLab = CrimeLab.get(getActivity());
        data.putExtra(ARG_CRIME_ID, mCrimeLab.getCrimeIndex(mCrime));
        getActivity().setResult(Activity.RESULT_OK, data);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();

        //UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        //mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        returnResult();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("CrimeFragment", "beforeTextChanged: "+charSequence+" start: "+i+" count: "+i1+" after: "+i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("CrimeFragment", "onTextChanged: "+charSequence+" start: "+i+" before: "+i1+" count: "+i2);
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("CrimeFragment", "Editable: "+editable);
            }
        });

        mDataButton = (Button)v.findViewById(R.id.crime_date);
        mDataButton.setText(mCrime.getDate().toString());
        mDataButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });
        return v;
    }

}
