package com.project.pp.parentparadise.lin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.pp.parentparadise.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActFragment extends Fragment {

    RadioGroup rgActivity;
    RadioButton rbList, rbHistory, rbWatch;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities, container, false);

        findviews();
        initContent();

        rgActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Fragment fragment;
                rbDefault();
                switch (checkedId)
                {
                    case R.id.rbList:
                        fragment = new ActListFragment();
                        switchFragment(fragment);
                        rbList.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtChecked));
                        rbBackgroundDefault();
                        rbList.setBackgroundResource(R.drawable.lin_rb_back);
                        break;
                    case R.id.rbHistory:
                        fragment = new ActHistoryFragment();
                        switchFragment(fragment);
                        rbHistory.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtChecked));
                        rbBackgroundDefault();
                        rbHistory.setBackgroundResource(R.drawable.lin_rb_back);
                        break;
                    case R.id.rbWatch:
                        fragment = new ActWatchFragment();
                        switchFragment(fragment);
                        rbWatch.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtChecked));
                        rbBackgroundDefault();
                        rbWatch.setBackgroundResource(R.drawable.lin_rb_back);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    private void rbBackgroundDefault() {
        rbList.setBackgroundResource(0);
        rbHistory.setBackgroundResource(0);
        rbWatch.setBackgroundResource(0);
    }

    private void findviews(){
        rgActivity = view.findViewById(R.id.rgActivity);
        rbList = view.findViewById(R.id.rbList);
        rbHistory = view.findViewById(R.id.rbHistory);
        rbWatch = view.findViewById(R.id.rbWatch);

    }

    private void rbDefault(){
        rbList.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtFalse));
        rbHistory.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtFalse));
        rbWatch.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtFalse));
    }

    private void initContent() {
        rbList.setTextColor(ContextCompat.getColor(getContext(), R.color.rbTxtChecked));
        rbList.setBackgroundResource(R.drawable.lin_rb_back);
        Fragment fragment = new ActListFragment();
        switchFragment(fragment);

    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityContent, fragment);
        fragmentTransaction.commit();

    }

}
