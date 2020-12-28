package ir.proglovving.fitapp.viesws.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.viesws.activities.CommonQuestionsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseTooltipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseTooltipFragment extends Fragment {

    private static final String ARG_EXERCISE = "exercise";

    private Exercise exercise;

    public ExerciseTooltipFragment() {
        // Required empty public constructor
    }

    public static ExerciseTooltipFragment newInstance(Exercise exercise) {
        ExerciseTooltipFragment fragment = new ExerciseTooltipFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise = (Exercise) getArguments().get(ARG_EXERCISE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_tooltip, container, false);

        ImageButton closeButton = view.findViewById(R.id.close_imageButton);
        ImageView exerciseGifImageView = view.findViewById(R.id.img_exerciseGif);
        TextView exerciseTitleTextView = view.findViewById(R.id.tv_exerciseTitle);
        TextView walkthroughTextView = view.findViewById(R.id.tv_walkthrough);
        Button faqButton = view.findViewById(R.id.btn_faq);

        Glide.with(exerciseGifImageView).load(exercise.getGif()).into(exerciseGifImageView);
        exerciseTitleTextView.setText(exercise.getTitle());
        walkthroughTextView.setText(exercise.getWalkthrough());

        closeButton.setOnClickListener(v -> getActivity().onBackPressed());
        faqButton.setOnClickListener(v -> CommonQuestionsActivity.start(getContext()));
        return view;
    }
}