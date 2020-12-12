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
import ir.proglovving.fitapp.adapters.PlanItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Category;

public class CategoryActivity extends AppCompatActivity implements Pagination {

    public static final String INTENT_KEY_CATEGORY_ID = "categoryId";
    public static final String INTENT_KEY_CATEGORY_NAME = "categoryName";

    private Toolbar toolbar;
    private RecyclerView planItemsRecyclerView;
    private ProgressBar progressBar;

    private PlanItemsRecyclerAdapter planItemsRecyclerAdapter;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String nextPage = "initial";

    public static void start(Context context, int categoryId, String categoryName) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(CategoryActivity.INTENT_KEY_CATEGORY_ID, categoryId);
        intent.putExtra(CategoryActivity.INTENT_KEY_CATEGORY_NAME, categoryName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        toolbar.setTitle(getIntent().getStringExtra(INTENT_KEY_CATEGORY_NAME));

        planItemsRecyclerAdapter = new PlanItemsRecyclerAdapter(this, this);
        planItemsRecyclerView.setAdapter(planItemsRecyclerAdapter);

        apiService = RetrofitClient.getApiService();
        onNextPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        planItemsRecyclerView = findViewById(R.id.plan_item_list_recycler_view);
        progressBar = findViewById(R.id.progressBar);

        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(CategoryActivity.this));
            }
        }, 10);
    }

    @Override
    public void onNextPage() {
        if(nextPage == null)
            return;

        progressBar.setVisibility(View.VISIBLE);
        Single<Category> categorySingleObservable;
        if (nextPage.equals("initial")) {
            categorySingleObservable = apiService.getCategory(getIntent().getIntExtra(INTENT_KEY_CATEGORY_ID, -1));
        }else{
            categorySingleObservable = apiService.getCategory(nextPage);
        }

        categorySingleObservable.subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS) // TODO: 12/7/20 remove this line later
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Category>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Category category) {
                        progressBar.setVisibility(View.GONE);
                        planItemsRecyclerAdapter.addItems(category.getPlanItemList());
                        nextPage = category.getNextPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        // TODO: 10/3/20
                        Toast.makeText(CategoryActivity.this, "failed to load category!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}