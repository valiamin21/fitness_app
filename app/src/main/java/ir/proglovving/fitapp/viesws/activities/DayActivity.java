package ir.proglovving.fitapp.viesws.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.ExerciseItemsRecyclerAdapter;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.viesws.fragments.ExerciseGifFragment;
import ir.proglovving.fitapp.viesws.fragments.ExerciseListFragment;
import ir.proglovving.fitapp.viesws.fragments.ExerciseRestFragment;
import ir.proglovving.fitapp.viesws.fragments.ExerciseTooltipFragment;

public class DayActivity extends AppCompatActivity implements ExerciseItemsRecyclerAdapter.ExerciseSelectionListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        int dayId = getIntent().getIntExtra(INTENT_KEY_DAY_ID, -1);
        String dayTitle = getIntent().getStringExtra(INTENT_KEY_DAY_TITLE);
        String dayMuscles = getIntent().getStringExtra(INTENT_KEY_DAY_MUSCLES);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, ExerciseListFragment.newInstance(dayId, dayTitle, dayMuscles))
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
}