package ir.proglovving.fitapp;

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

import ir.proglovving.fitapp.adapters.CategoryItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ConstraintLayout splashContainer;
    private TextView motivationSentenceTextView;
    private RecyclerView categoryItemListRecyclerView;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        final Call<CategoriesPack> categoriesPackCall = apiService.getCategories();
        categoriesPackCall.enqueue(new Callback<CategoriesPack>() {
            @Override
            public void onResponse(Call<CategoriesPack> call, Response<CategoriesPack> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "error code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                appBarLayout.setVisibility(View.VISIBLE);
                splashContainer.setVisibility(View.GONE);
                CategoriesPack categoriesPack = response.body();
                CategoryItemsRecyclerAdapter categoryItemsRecyclerAdapter = new CategoryItemsRecyclerAdapter(MainActivity.this,categoriesPack.getCategoryItemList());
                categoryItemListRecyclerView.setAdapter(categoryItemsRecyclerAdapter);

            }

            @Override
            public void onFailure(Call<CategoriesPack> call, Throwable t) {
                // TODO: 9/30/20
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
    }
}