package ir.proglovving.fitapp.viesws.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.ExerciseItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.DayExercisesRequest;
import ir.proglovving.fitapp.viesws.fragments.ExerciseListFragment;

public class DayActivity extends AppCompatActivity{

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
                .commit();

    }
}