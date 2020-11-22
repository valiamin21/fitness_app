package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.ExerciseItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.DayExercises;
import ir.proglovving.fitapp.data_models.Exercise;

public class DayActivity extends AppCompatActivity {

    public static final String INTENT_KEY_DAY_ID = "dayId";
    public static final String INTENT_KEY_DAY_TITLE = "dayTitle";
    public static final String INTENT_KEY_DAY_MUSCLES = "dayMuscles";


    private Toolbar toolbar;
    private RecyclerView exercisesRecyclerView;
    private ExerciseItemsRecyclerAdapter exerciseItemsRecyclerAdapter;
    private Disposable disposable;

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

        initViews();

        int dayId = getIntent().getIntExtra(INTENT_KEY_DAY_ID, -1);
        String dayTitle = getIntent().getStringExtra(INTENT_KEY_DAY_TITLE);

        toolbar.setTitle(dayTitle);

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getDayExercises(dayId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DayExercises>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(DayExercises dayExercises) {
                        exerciseItemsRecyclerAdapter = new ExerciseItemsRecyclerAdapter(DayActivity.this, dayExercises.getExerciseList());
                        exercisesRecyclerView.setAdapter(exerciseItemsRecyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DayActivity.this, "error in loading day exercises", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        exercisesRecyclerView = findViewById(R.id.exercise_items_list_recyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}