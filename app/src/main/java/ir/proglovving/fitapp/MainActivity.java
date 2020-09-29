package ir.proglovving.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView motivationSentenceTextView;

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

                Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                CategoriesPack categoriesPack = response.body();
//                motivationSentenceTextView.setText("count: " + categoriesPack.getCount() + "\n");
//                motivationSentenceTextView.append("\n[\n\n");
//                for(CategoryItem categoryItem : categoriesPack.getCategoryItemList()){
//                    motivationSentenceTextView.append("id: " + categoryItem.getId() + "\n");
//                    motivationSentenceTextView.append("name: " + categoryItem.getName() + "\n");
//                    motivationSentenceTextView.append("image: " + categoryItem.getImage() + "\n\n");
//                }
//                motivationSentenceTextView.append("]");

            }

            @Override
            public void onFailure(Call<CategoriesPack> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void initViews() {
        motivationSentenceTextView = findViewById(R.id.motivation_sentence_tv);
    }
}