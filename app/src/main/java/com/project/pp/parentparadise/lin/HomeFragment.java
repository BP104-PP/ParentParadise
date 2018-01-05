package com.project.pp.parentparadise.lin;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.project.pp.parentparadise.R;
import com.project.pp.parentparadise.ppmain.Member;
import com.project.pp.parentparadise.utl.SessionManager;

import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    SessionManager session;
    private ImageSwitcher imageSwitcher;
    private int[] gallery = { R.drawable.lin_slide_image04, R.drawable.lin_slide_image02 };
    private int position;
    private static final Integer DURATION = 2500;
    private Timer timer = null;
    TextView tvHello;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        session = new SessionManager(getActivity().getApplicationContext());
        Member loginMember = session.getLoginMemberInfo();
        String first_name = loginMember.getFirst_name();

        findViews(view);
        tvHello.setText("Hi, " + first_name);

        //Gallery gallery = view.findViewById(R.id.gallery);
        //ImageView ivSlide = view.findViewById(R.id.ivSlide);

        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView i = new ImageView(getActivity());
                i.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return i;
            }
        });

//        tvHello.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (timer != null) {
//                    timer.cancel();
//                }
//                position = 0;
//                startSlider();
//            }
//        });

        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id;
                if(position==1){
                    id = 22;
                }else{
                    id = 23;
                }

                Intent intent = new Intent(getContext(), ActDetail.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

//        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.);
//        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
//        imageSwitcher.setInAnimation(fadeIn);
//        imageSwitcher.setOutAnimation(fadeOut);
        if(Build.VERSION.SDK_INT>=21){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(5);
        }

        return view;
    }

    private void findViews(View view) {
        tvHello = view.findViewById(R.id.tvHello);
    }

    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (timer != null) {
//
//        }
        startSlider();

    }
}
