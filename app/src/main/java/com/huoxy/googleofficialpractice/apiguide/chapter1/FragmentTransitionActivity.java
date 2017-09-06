package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.huoxy.googleofficialpractice.R;

public class FragmentTransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4;
    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transition);

        initViews();

        manager = getSupportFragmentManager();
    }

    private void initViews() {
        button1 = (Button) findViewById(R.id.fragment_transition_1);
        button2 = (Button) findViewById(R.id.fragment_transition_2);
        button3 = (Button) findViewById(R.id.fragment_transition_3);
        button4 = (Button) findViewById(R.id.fragment_transition_4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        addGreenFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_transition_1:
                addRedFragment();
                break;
            case R.id.fragment_transition_2:
                addBlueFragment();
                break;
            case R.id.fragment_transition_3:
                addYellowFragment();
                break;
            case R.id.fragment_transition_4:
                addGreenFragment();
                break;
        }
    }

    private void addRedFragment() {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.container, TransitionFragment.newInstance("Open Transition", "red"), "red");
        transaction.commit();
    }

    private void addBlueFragment() {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, TransitionFragment.newInstance("Fade Transition", "blue"), "blue");
        transaction.commit();
    }

    private void addYellowFragment() {
        transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.container, TransitionFragment.newInstance("Close Transition", "yellow"), "yellow");
        transaction.commit();
    }

    private void addGreenFragment() {
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.bottom_in, R.anim.top_out);
        transaction.replace(R.id.container, TransitionFragment.newInstance("Custom Transition", "green"), "green");
        transaction.commit();
    }
}
