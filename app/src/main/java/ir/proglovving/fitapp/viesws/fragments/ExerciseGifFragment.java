package ir.proglovving.fitapp.viesws.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.viesws.activities.CommonQuestionsActivity;


public class ExerciseGifFragment extends Fragment {

    private static final String ARG_EXERCISE = "exercise";

    private Exercise exercise;

    public ExerciseGifFragment() {
        // Required empty public constructor
    }


    public static ExerciseGifFragment newInstance(Exercise exercise) {
        ExerciseGifFragment fragment = new ExerciseGifFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise = (Exercise) getArguments().getSerializable(ARG_EXERCISE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_gif, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton faqButton = view.findViewById(R.id.btn_faq);
        ImageView exerciseGifImageView = view.findViewById(R.id.img_exerciseGif);
        TextView exerciseTitleTextView = view.findViewById(R.id.tv_exerciseTitle);
        TextView timeTextView = view.findViewById(R.id.tv_time);
        ImageButton previousExerciseButton = view.findViewById(R.id.btn_previous_exercise);
        ImageButton nextExerciseButton = view.findViewById(R.id.btn_next_exercise);
        ImageButton resumeAndPauseImageButton = view.findViewById(R.id.btn_resumeAndPause);
        FloatingActionButton checkFab = view.findViewById(R.id.check_fab);

        // TODO: 1/7/21 implementing events (next exercise, previous exercise, pause, resume, checkFab)
        Glide.with(exerciseGifImageView).load(exercise.getGif()).into(exerciseGifImageView);
        exerciseTitleTextView.setText(exercise.getTitle());

        if (exercise.isTimed()) {
            timeTextView.setText(exercise.getTime() + " ثانیه");
            checkFab.setVisibility(View.INVISIBLE);
            resumeAndPauseImageButton.setVisibility(View.VISIBLE);
        } else {
            timeTextView.setText(exercise.getTime() + " تا");
            checkFab.setVisibility(View.VISIBLE);
            resumeAndPauseImageButton.setVisibility(View.INVISIBLE);
        }

        // TODO: 1/7/21 implementing counter for timed exercises


        faqButton.setOnClickListener(v -> CommonQuestionsActivity.start(getContext()));
        // TODO: 1/7/21 showing dialog and asking user to exit (give up)
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().hide(this).commit());

        return view;
    }
}