package ir.proglovving.fitapp.viesws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Random;

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
import ir.proglovving.fitapp.data_models.Tip;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ConstraintLayout splashContainer;
    private TextView motivationSentenceTextView;
    private RecyclerView categoryItemListRecyclerView;
    private CategoryItemsRecyclerAdapter categoryItemsRecyclerAdapter;
    private AppBarLayout appBarLayout;
    private NavigationView navigationView;

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
                .subscribe(new SingleObserver<List<Tip>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        tipsDisposable = d;
                    }

                    @Override
                    public void onSuccess(List<Tip> tips) {
                        motivationSentenceTextView.setText(tips.get(new Random(SystemClock.currentThreadTimeMillis()).nextInt(tips.size())).getText());
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


        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(CategoriesActivity.this, "clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}