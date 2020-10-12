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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ConstraintLayout splashContainer;
    private TextView motivationSentenceTextView;
    private RecyclerView categoryItemListRecyclerView;
    CategoryItemsRecyclerAdapter categoryItemsRecyclerAdapter;
    private AppBarLayout appBarLayout;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        ApiService apiService = RetrofitClient.getApiService();
        final Single<CategoriesPack> categoriesPackCall = apiService.getCategories();
        categoriesPackCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesPack>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(CategoriesPack categoriesPack) {
                        appBarLayout.setVisibility(View.VISIBLE);
                        splashContainer.setVisibility(View.GONE);
                        categoryItemsRecyclerAdapter = new CategoryItemsRecyclerAdapter(MainActivity.this, categoriesPack.getCategoryItemList());
                        categoryItemListRecyclerView.setAdapter(categoryItemsRecyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
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
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(MainActivity.this));
            }
        }, 10);

    }
}