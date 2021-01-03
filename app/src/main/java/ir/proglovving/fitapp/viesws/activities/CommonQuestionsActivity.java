package ir.proglovving.fitapp.viesws.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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
import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.FaqItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.FaqItemsRequest;

public class CommonQuestionsActivity extends AppCompatActivity implements Pagination {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CommonQuestionsActivity.class));
    }

    private Toolbar toolbar;
    private RecyclerView faqRecyclerView;
    private ProgressBar paginationProgressBar;

    private FaqItemsRecyclerAdapter faqItemsRecyclerAdapter;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String next = "initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_questions);
        initViews();

        faqItemsRecyclerAdapter = new FaqItemsRecyclerAdapter(this, this);
        faqRecyclerView.setAdapter(faqItemsRecyclerAdapter);

        apiService = RetrofitClient.getApiService();
        onNextPage();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        faqRecyclerView = findViewById(R.id.faq_recyclerView);
        paginationProgressBar = findViewById(R.id.progressBar_pagination);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onNextPage() {
        if (next == null)
            return;

        paginationProgressBar.setVisibility(View.VISIBLE);
        Single<FaqItemsRequest> faqItemsRequestSingle;
        if (next.equals("initial")) {
            faqItemsRequestSingle = apiService.getFaq();
        } else {
            faqItemsRequestSingle = apiService.getFaq(next);
        }


        faqItemsRequestSingle
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FaqItemsRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FaqItemsRequest faqItemsRequest) {
                        paginationProgressBar.setVisibility(View.INVISIBLE);
                        faqItemsRecyclerAdapter.addItems(faqItemsRequest.getFaqItemList());
                        next = faqItemsRequest.getNextPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CommonQuestionsActivity.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}