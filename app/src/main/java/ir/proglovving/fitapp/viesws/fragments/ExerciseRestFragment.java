package ir.proglovving.fitapp.viesws.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.Exercise;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseRestFragment extends Fragment {

    private static final String ARG_REST_TIME = "restTime";
    private static final String ARG_NEXT_EXERCISE = "nextExercise";

    private int restTime;
    private Exercise nextExercise;

    public ExerciseRestFragment() {
        // Required empty public constructor
    }

    public static ExerciseRestFragment newInstance(int restTime, Exercise nextExercise) {
        ExerciseRestFragment fragment = new ExerciseRestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_REST_TIME, restTime);
        args.putSerializable(ARG_NEXT_EXERCISE, nextExercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restTime = getArguments().getInt(ARG_REST_TIME);
            nextExercise = (Exercise) getArguments().get(ARG_NEXT_EXERCISE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_rest, container, false);

        TextView restTimeTextView = view.findViewById(R.id.tv_restRemainingTime);
        restTimeTextView.setText(String.format("%02d:%02d", restTime / 60, restTime % 60));

        ImageView nextExerciseImage = view.findViewById(R.id.img_nextExercise);
        Glide.with(nextExerciseImage)
                .load(nextExercise.getGif())
                .into(nextExerciseImage);

        TextView nextProgressTextView = view.findViewById(R.id.tv_nextProgress);
        nextProgressTextView.setText(getContext().getString(R.string.next) + " 4/14"); // TODO: 12/16/20 refactore this line later

        TextView nextExerciseTitle = view.findViewById(R.id.tv_nextExerciseTitle);
        nextExerciseTitle.setText(nextExercise.getTitle());

        TextView nextExerciseTimeTextView = view.findViewById(R.id.tv_nextExerciseTime);
        nextExerciseTimeTextView.setText(String.format("%02d:%02d", nextExercise.getTime() / 60, nextExercise.getTime() % 60)); // TODO: 12/16/20 write this code for two position of isTime bool

        return view;
    }
}