package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.CategoryItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import ir.proglovving.fitapp.data_models.TipsRequestModel;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ConstraintLayout splashContainer;
    private TextView motivationSentenceTextView;
    private RecyclerView categoryItemListRecyclerView;
    CategoryItemsRecyclerAdapter categoryItemsRecyclerAdapter;
    private AppBarLayout appBarLayout;

    private ApiService apiService;
    private Disposable tipsDisposable;
    private Disposable categoriesDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        apiService = RetrofitClient.getApiService();

        loadTips();
    }

    private void loadTips() {
        apiService.getTips().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TipsRequestModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        tipsDisposable = d;
                    }

                    @Override
                    public void onSuccess(TipsRequestModel tipsRequestModel) {
                        motivationSentenceTextView.setText(tipsRequestModel.getTip().getText());
                        loadCategories();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CategoriesActivity.this, "error in loading tips", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadCategories() {
        final Single<CategoriesPack> categoriesPackCall = apiService.getCategories();
        categoriesPackCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesPack>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        categoriesDisposable = d;
                    }

                    @Override
                    public void onSuccess(CategoriesPack categoriesPack) {
                        tipsDisposable.dispose();

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        appBarLayout.setVisibility(View.VISIBLE);
                        splashContainer.setVisibility(View.GONE);
                        categoryItemsRecyclerAdapter = new CategoryItemsRecyclerAdapter(CategoriesActivity.this, categoriesPack.getCategoryItemList());
                        categoryItemListRecyclerView.setAdapter(categoryItemsRecyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CategoriesActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoriesDisposable.dispose();
        tipsDisposable.dispose();
    }

    private void initViews() {
        splashContainer = findViewById(R.id.splash_container);
        motivationSentenceTextView = findViewById(R.id.motivation_sentence_tv);
        categoryItemListRecyclerView = findViewById(R.id.category_item_list_recycler_view);
        appBarLayout = findViewById(R.id.appbarLayout);
        appBarLayout.setVisibility(View.INVISIBLE);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(CategoriesActivity.this));
            }
        }, 10);

    }
}