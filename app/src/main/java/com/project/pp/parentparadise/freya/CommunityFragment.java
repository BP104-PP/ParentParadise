package com.project.pp.parentparadise.freya;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.pp.parentparadise.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {

    RadioGroup community_bar_item;
    RadioButton com_family, com_chat;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         view = inflater.inflate(R.layout.fragment_community_main, container, false);

        //findviews();
        community_bar_item =  view.findViewById(R.id.community_bar_item);
        com_family = view.findViewById(R.id.com_family);
        com_chat = view.findViewById(R.id.com_chat);


        init_ComContent();


        community_bar_item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment;
                ComDefault();
                switch (checkedId) {
                    case R.id.com_family:
                        fragment = new CommunityFragment_family();
                        switchFragment(fragment);
                        com_family.setTextColor(Color.WHITE);
                        break;
                    case R.id.com_chat:
                        fragment = new CommunityFragment_chatlist();
                        switchFragment(fragment);
                        com_chat.setTextColor(Color.WHITE);
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    private void findviews() {


    }

    private void ComDefault() {
        com_family.setTextColor(Color.BLACK);
        com_chat.setTextColor(Color.BLACK);
    }

    //        設某個頁面為初始畫面 點進去字變白色

    private void init_ComContent() {
        com_family.setTextColor(Color.WHITE);
        Fragment fragment = new CommunityFragment_family();
        switchFragment(fragment);

//        初始化的畫面

    }
//   switchFragment 抽取換新的頁面～

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.init_List, fragment);
        fragmentTransaction.commit();


    }

}

