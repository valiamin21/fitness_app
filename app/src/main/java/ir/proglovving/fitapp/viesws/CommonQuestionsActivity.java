package ir.proglovving.fitapp.viesws;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import ir.proglovving.cfviews.CTypefaceProvider;
import ir.proglovving.fitapp.R;

public class CommonQuestionsActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CommonQuestionsActivity.class));
    }

    private Toolbar toolbar;
    private RecyclerView faqRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_questions);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                CTypefaceProvider.applyFontForAViewGroup(toolbar, CTypefaceProvider.getVazir(CommonQuestionsActivity.this));
            }
        }, 10);

        faqRecyclerView = findViewById(R.id.faq_recyclerView);
    }
}