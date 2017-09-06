package com.huoxy.googleofficialpractice.apiguide.chapter1;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huoxy.googleofficialpractice.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransitionFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_BACKGROUND = "background";

    private String mTitle;
    private String mBackground;


    public TransitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title      title
     * @param background background (red\blue\yellow\other)
     * @return A new instance of fragment TransitionFragment.
     */
    public static TransitionFragment newInstance(String title, @NonNull String background) {
        TransitionFragment fragment = new TransitionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_BACKGROUND, background);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mBackground = getArguments().getString(ARG_BACKGROUND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transition1, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(mTitle);
        if ("red".equalsIgnoreCase(mBackground)) {
            titleView.setBackgroundColor(Color.RED);
        } else if ("blue".equalsIgnoreCase(mBackground)) {
            titleView.setBackgroundColor(Color.BLUE);
        } else if ("yellow".equalsIgnoreCase(mBackground)) {
            titleView.setBackgroundColor(Color.YELLOW);
        } else {
            titleView.setBackgroundColor(Color.GREEN);
        }

        return view;
    }

}
