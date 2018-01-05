package com.project.pp.parentparadise.amber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.pp.parentparadise.R;

import java.lang.reflect.Field;

/**
 * Created by Amber on 2017/12/5.
 */

public class ShareFragment extends Fragment {

    RadioGroup rgShare;
    RadioButton rbSweet, rbAct, rbBook, rbMedia, rbLife;
    View view;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.share_main, container, false);
        findviews(view);
        initContent();
        rgShare.setOnCheckedChangeListener(rgListener);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShareAddArticle.class);
                startActivity(intent);
            }
        });
    }

    private void findviews(View view) {
        fabAdd = view.findViewById(R.id.fabAdd);
        rgShare = view.findViewById(R.id.rgShare);
        rbSweet = view.findViewById(R.id.rbSweet);
        rbAct = view.findViewById(R.id.rbAct);
        rbBook = view.findViewById(R.id.rbBook);
        rbMedia = view.findViewById(R.id.rbMedia);
        rbLife = view.findViewById(R.id.rbLife);

    }

    private void rbDefault() {
        rbSweet.setTextColor(Color.BLACK);
        rbAct.setTextColor(Color.BLACK);
        rbBook.setTextColor(Color.BLACK);
        rbMedia.setTextColor(Color.BLACK);
        rbLife.setTextColor(Color.BLACK);
    }

    private void initContent() {
        rbSweet.setTextColor(Color.WHITE);
        Fragment fragment = new ShareFragmentSweet();
        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.llContent, fragment);
        fragmentTransaction.commit();
    }

    RadioGroup.OnCheckedChangeListener rgListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Fragment fragment;
            rbDefault();
            switch (checkedId) {
                case R.id.rbSweet:
                    fragment = new ShareFragmentSweet();
                    switchFragment(fragment);
                    rbSweet.setTextColor(Color.WHITE);
                    break;
                case R.id.rbAct:
                    fragment = new ShareFragmentAct();
                    switchFragment(fragment);
                    rbAct.setTextColor(Color.WHITE);
                    break;
                case R.id.rbBook:
                    fragment = new ShareFragmentBook();
                    switchFragment(fragment);
                    rbBook.setTextColor(Color.WHITE);
                    break;
                case R.id.rbMedia:
                    fragment = new ShareFragmentMedia();
                    switchFragment(fragment);
                    rbMedia.setTextColor(Color.WHITE);
                    break;
                case R.id.rbLife:
                    fragment = new ShareFragmentLife();
                    switchFragment(fragment);
                    rbLife.setTextColor(Color.WHITE);
                    break;
                default:
                    break;
            }
        }
    };
}
