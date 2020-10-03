package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.PlanItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanItemsActivity extends AppCompatActivity {

    public static final String INTENT_KEY_CATEGORY_ID = "categoryId";

    private Toolbar toolbar;
    private RecyclerView planItemsRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_items);
        initViews();

        ApiService apiService = RetrofitClient.getApiService();
        Call<Category> categoryCall = apiService.getCategory(getIntent().getIntExtra(INTENT_KEY_CATEGORY_ID,-1));
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                progressBar.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(PlanItemsActivity.this, "error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                PlanItemsRecyclerAdapter planItemsRecyclerAdapter = new PlanItemsRecyclerAdapter(PlanItemsActivity.this,response.body().getPlanItemList());
                planItemsRecyclerView.setAdapter(planItemsRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // TODO: 10/3/20
                Toast.makeText(PlanItemsActivity.this, "failed to load category!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        planItemsRecyclerView = findViewById(R.id.plan_item_list_recycler_view);
        progressBar = findViewById(R.id.progressBar);
    }
}