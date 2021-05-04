package com.example.project3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment which simply displays the text "Me Fragment" and does nothing else. We created this fragment
 * because it added another icon to our Bottom Navigation view but we did not have time to implement it. If
 * we had more time then we would have added the ability for the user to see things about themselves such as
 * their username and account information but also information about their fitness, weight, age, gender,
 * physical activity level, and fitness and weight goals. This would allow us to customize the nutrition requirements
 * for that user.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class Me_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment, container,false);
    }
}
