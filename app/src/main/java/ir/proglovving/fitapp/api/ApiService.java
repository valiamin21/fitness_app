package ir.proglovving.fitapp.api;

import ir.proglovving.fitapp.data_models.CategoriesPack;
import ir.proglovving.fitapp.data_models.Category;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("categories")
    Call<CategoriesPack> getCategories();

    @GET("category/{id}")
    Call<Category> getCategory(@Path("id") int categoryId);
}
