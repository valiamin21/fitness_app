package ir.proglovving.fitapp.api;

import io.reactivex.Single;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import ir.proglovving.fitapp.data_models.Category;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("categories")
    Single<CategoriesPack> getCategories();

    @GET("category/{id}")
    Single<Category> getCategory(@Path("id") int categoryId);
}
