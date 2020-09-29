package ir.proglovving.fitapp.api;

import ir.proglovving.fitapp.data_models.CategoriesPack;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("categories")
    Call<CategoriesPack> getCategories();
}
