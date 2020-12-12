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

public class DayActivity extends AppCompatActivity implements Pagination {

    public static final String INTENT_KEY_DAY_ID = "dayId";
    public static final String INTENT_KEY_DAY_TITLE = "dayTitle";
    public static final String INTENT_KEY_DAY_MUSCLES = "dayMuscles";


    private Toolbar toolbar;
    private RecyclerView exercisesRecyclerView;
    private ProgressBar paginationProgressBar;

    private ExerciseItemsRecyclerAdapter exerciseItemsRecyclerAdapter;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String next = "initial";

    private int dayId;

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

        exerciseItemsRecyclerAdapter = new ExerciseItemsRecyclerAdapter(DayActivity.this, this);
        exercisesRecyclerView.setAdapter(exerciseItemsRecyclerAdapter);

        dayId = getIntent().getIntExtra(INTENT_KEY_DAY_ID, -1);
        String dayTitle = getIntent().getStringExtra(INTENT_KEY_DAY_TITLE);

        toolbar.setTitle(dayTitle);

        apiService = RetrofitClient.getApiService();
        onNextPage();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(DayActivity.this));
            }
        }, 10);
        exercisesRecyclerView = findViewById(R.id.exercise_items_list_recyclerView);
        paginationProgressBar = findViewById(R.id.progressBar_pagination);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onNextPage() {
        if(next == null)
            return;

        paginationProgressBar.setVisibility(View.VISIBLE);
        Single<DayExercisesRequest> dayExercisesRequestSingleObservable;
        if(next.equals("initial")){
            dayExercisesRequestSingleObservable = apiService.getDayExercises(dayId);
        }else{
            dayExercisesRequestSingleObservable = apiService.getDayExercises(next);
        }

        dayExercisesRequestSingleObservable
                .delay(1, TimeUnit.SECONDS) // TODO: 12/7/20 remove this line later
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DayExercisesRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(DayExercisesRequest dayExercisesRequest) {
                        paginationProgressBar.setVisibility(View.INVISIBLE);
                        next = dayExercisesRequest.getNextPage();
                        exerciseItemsRecyclerAdapter.addItems(dayExercisesRequest.getExerciseList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DayActivity.this, "error in loading day exercises", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}