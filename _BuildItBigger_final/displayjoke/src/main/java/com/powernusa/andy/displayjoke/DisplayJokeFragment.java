package com.powernusa.andy.displayjoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andy on 10/4/2016.
 */
public class DisplayJokeFragment extends Fragment {


    private TextView mDisplayJoke;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_displayjoke,container,false);
        mDisplayJoke = (TextView) view.findViewById(R.id.tv_display_joke);
        if(getActivity().getIntent() != null){
            String aJoke = getActivity().getIntent().getStringExtra(DisplayJokeActivity.EXTRA_JOKE_KEY);
            mDisplayJoke.setText(aJoke);
        }
        return view;
    }
}
