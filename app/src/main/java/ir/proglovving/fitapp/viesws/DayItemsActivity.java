package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.DayItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.Plan;

public class DayItemsActivity extends AppCompatActivity {

    public static final String INTENT_KEY_PLAN_ID = "plan_id";

    private Toolbar toolbar;
    private RecyclerView dayItemsRecyclerView;
    private DayItemsRecyclerAdapter dayItemsRecyclerAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void start(Context context, int planId) {
        Intent starter = new Intent(context, DayItemsActivity.class);
        starter.putExtra(INTENT_KEY_PLAN_ID, planId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_items);
        initViews();

        ApiService apiService = RetrofitClient.getApiService();
        Single<Plan> planCall = apiService.getPlan(getIntent().getIntExtra(INTENT_KEY_PLAN_ID, -1));
        planCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Plan>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Plan plan) {
                        applyPlan(plan);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DayItemsActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void applyPlan(Plan plan) {
        toolbar.setTitle(plan.getTitle());
        getDaysObservable(RetrofitClient.getApiService(), plan.getDaysIds())
                .subscribe(new Observer<Day>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Day day) {
                        dayItemsRecyclerAdapter.addItem(day);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 10/13/20
                    }

                    @Override
                    public void onComplete() {
                        // TODO: 10/13/20
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private Observable<Day> getDaysObservable(final ApiService apiService, final int[] days) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int day : days) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(day);
                    }
                }
            }
        }).concatMap(new Function<Integer, ObservableSource<? extends Day>>() {
            @Override
            public ObservableSource<? extends Day> apply(Integer integer) throws Exception {
                return apiService.getDay(integer);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(DayItemsActivity.this));
            }
        }, 10);
        dayItemsRecyclerView = findViewById(R.id.day_items_list_recyclerView);
        dayItemsRecyclerAdapter = new DayItemsRecyclerAdapter(this);
        dayItemsRecyclerView.setAdapter(dayItemsRecyclerAdapter);
    }
}