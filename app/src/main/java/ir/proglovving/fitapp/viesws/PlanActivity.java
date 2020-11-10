package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

public class PlanActivity extends AppCompatActivity {

    public static final String INTENT_KEY_PLAN_ID = "plan_id";

    private Toolbar toolbar;
    private RecyclerView dayItemsRecyclerView;
    private DayItemsRecyclerAdapter dayItemsRecyclerAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void start(Context context, int planId) {
        Intent starter = new Intent(context, PlanActivity.class);
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
                        toolbar.setTitle(plan.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PlanActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(PlanActivity.this));
            }
        }, 10);
        dayItemsRecyclerView = findViewById(R.id.day_items_list_recyclerView);
        dayItemsRecyclerAdapter = new DayItemsRecyclerAdapter(this);
        dayItemsRecyclerView.setAdapter(dayItemsRecyclerAdapter);
    }
}