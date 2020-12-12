package ir.proglovving.fitapp.viesws.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import ir.proglovving.fitapp.adapters.DayItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Plan;
import ir.proglovving.fitapp.data_models.PlanDaysRequest;

public class PlanActivity extends AppCompatActivity implements Pagination {

    private static final String TAG = PlanActivity.class.getSimpleName();

    public static final String INTENT_KEY_PLAN_ID = "plan_id";

    private Toolbar toolbar;
    private RecyclerView dayItemsRecyclerView;
    private ProgressBar paginationProgressBar;

    private DayItemsRecyclerAdapter dayItemsRecyclerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiService;
    private String next="initial";

    public static void start(Context context, int planId) {
        Intent starter = new Intent(context, PlanActivity.class);
        starter.putExtra(INTENT_KEY_PLAN_ID, planId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        initViews();

        dayItemsRecyclerAdapter = new DayItemsRecyclerAdapter(this, this);
        dayItemsRecyclerView.setAdapter(dayItemsRecyclerAdapter);

        apiService = RetrofitClient.getApiService();
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

        onNextPage();
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
        paginationProgressBar = findViewById(R.id.progressBar_pagination);
    }

    @Override
    public void onNextPage() {
        if(next == null)
            return;
        paginationProgressBar.setVisibility(View.VISIBLE);
        final Single<PlanDaysRequest> planDaysSingleObservable;
        if(next.equals("initial")){
            planDaysSingleObservable = apiService.getPlanDays(getIntent().getIntExtra(INTENT_KEY_PLAN_ID, -1));
        }else{
            planDaysSingleObservable = apiService.getPlanDays(next);
        }
        planDaysSingleObservable.subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PlanDaysRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(PlanDaysRequest planDaysRequest) {
                        dayItemsRecyclerAdapter.addItems(planDaysRequest.getDayItemList());
                        next = planDaysRequest.getNextPage();
                        paginationProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PlanActivity.this, "error in loading plan days", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
    }
}