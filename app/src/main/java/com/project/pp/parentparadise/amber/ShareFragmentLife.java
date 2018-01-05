package com.project.pp.parentparadise.amber;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.pp.parentparadise.R;

/**
 * Created by Amber on 2017/12/5.
 */

public class ShareFragmentLife extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_recyclerview, container, false);
        textView = view.findViewById(R.id.textView);
        return view;
    }
}
