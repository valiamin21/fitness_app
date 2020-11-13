package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.PlanItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Category;

public class CategoryActivity extends AppCompatActivity {

    public static final String INTENT_KEY_CATEGORY_ID = "categoryId";
    public static final String INTENT_KEY_CATEGORY_NAME = "categoryName";

    private Toolbar toolbar;
    private RecyclerView planItemsRecyclerView;
    private ProgressBar progressBar;

    private Disposable disposable;

    public static void start(Context context, int categoryId, String categoryName) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(CategoryActivity.INTENT_KEY_CATEGORY_ID, categoryId);
        intent.putExtra(CategoryActivity.INTENT_KEY_CATEGORY_NAME, categoryName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_items);
        initViews();

        toolbar.setTitle(getIntent().getStringExtra(INTENT_KEY_CATEGORY_NAME));

        ApiService apiService = RetrofitClient.getApiService();
        Single<Category> categoryCall = apiService.getCategory(getIntent().getIntExtra(INTENT_KEY_CATEGORY_ID, -1));

        categoryCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Category>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(Category category) {
                        progressBar.setVisibility(View.GONE);
                        PlanItemsRecyclerAdapter planItemsRecyclerAdapter = new PlanItemsRecyclerAdapter(CategoryActivity.this, category.getPlanItemList());
                        planItemsRecyclerView.setAdapter(planItemsRecyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        // TODO: 10/3/20
                        Toast.makeText(CategoryActivity.this, "failed to load category!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
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
}