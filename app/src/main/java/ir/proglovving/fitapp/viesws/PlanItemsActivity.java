package ir.proglovving.fitapp.viesws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import ir.proglovving.fitapp.R;

public class PlanItemsActivity extends AppCompatActivity {

    public static final String INTENT_KEY_CATEGORY_ID = "categoryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_items);

        Toast.makeText(this, "" + getIntent().getIntExtra(INTENT_KEY_CATEGORY_ID,-1), Toast.LENGTH_SHORT).show();
    }
}