package ir.proglovving.fitapp.viesws.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.ExerciseItemsRecyclerAdapter;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.viesws.fragments.ExerciseGifFragment;
import ir.proglovving.fitapp.viesws.fragments.ExerciseListFragment;
import ir.proglovving.fitapp.viesws.fragments.ExerciseTooltipFragment;

public class DayActivity extends AppCompatActivity implements ExerciseItemsRecyclerAdapter.ExerciseSelectionListener, ExerciseListFragment.OnExerciseStartListener {

    public static final String INTENT_KEY_DAY_ID = "dayId";
    public static final String INTENT_KEY_DAY_TITLE = "dayTitle";
    public static final String INTENT_KEY_DAY_MUSCLES = "dayMuscles";


    public static void start(Context context, Day day) {
        Intent intent = new Intent(context, DayActivity.class);
        intent.putExtra(INTENT_KEY_DAY_ID, day.getId());
        intent.putExtra(INTENT_KEY_DAY_TITLE, day.getTitle());
        intent.putExtra(INTENT_KEY_DAY_MUSCLES, day.getMuscle());
        context.startActivity(intent);
    }

    public static List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseListFragment exerciseListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        int dayId = getIntent().getIntExtra(INTENT_KEY_DAY_ID, -1);
        String dayTitle = getIntent().getStringExtra(INTENT_KEY_DAY_TITLE);
        String dayMuscles = getIntent().getStringExtra(INTENT_KEY_DAY_MUSCLES);

        exerciseListFragment = ExerciseListFragment.newInstance(dayId, dayTitle, dayMuscles);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, exerciseListFragment)
                // TODO: 12/28/20 showing RestDayFragment when required
//                .add(R.id.fragment_container, RestDayFragment.newInstance())
                .commit();

    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_container, ExerciseTooltipFragment.newInstance(exercise))
                .commit();
    }

    @Override
    public void onExerciseStart() {
        // TODO: 1/2/21 implement body of this method
        // TODO: 1/7/21 starting ExerciseRestFragment at first
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(
                        R.id.fragment_container,
                        ExerciseGifFragment.newInstance(exerciseList.get(0))
                ).commit();
    }

    @Override
    public void onExerciseResume() {
        // TODO: 1/2/21 implement body of this method
        Toast.makeText(this, "exercise resume", Toast.LENGTH_SHORT).show();
    }
}