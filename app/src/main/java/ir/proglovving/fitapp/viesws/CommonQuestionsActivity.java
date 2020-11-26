package ir.proglovving.fitapp.viesws;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.FaqItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.FaqItemsRequest;

public class CommonQuestionsActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CommonQuestionsActivity.class));
    }

    private Toolbar toolbar;
    private RecyclerView faqRecyclerView;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_questions);
        initViews();

        ApiService apiService = RetrofitClient.getApiService();
        Single<FaqItemsRequest> faqItemsRequestSingle = apiService.getFaq();
        faqItemsRequestSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FaqItemsRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(FaqItemsRequest faqItemsRequest) {
                        FaqItemsRecyclerAdapter faqItemsRecyclerAdapter = new FaqItemsRecyclerAdapter(CommonQuestionsActivity.this, faqItemsRequest.getFaqItemList());
                        faqRecyclerView.setAdapter(faqItemsRecyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CommonQuestionsActivity.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(CommonQuestionsActivity.this));
            }
        }, 10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        faqRecyclerView = findViewById(R.id.faq_recyclerView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}