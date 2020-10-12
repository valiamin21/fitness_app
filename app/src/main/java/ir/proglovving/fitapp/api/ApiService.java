package ir.proglovving.fitapp.api;

import io.reactivex.Single;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import ir.proglovving.fitapp.data_models.Category;
import ir.proglovving.fitapp.data_models.Plan;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("categories")
    Single<CategoriesPack> getCategories();

    @GET("category/{id}")
    Single<Category> getCategory(@Path("id") int categoryId);

    @GET("plan/{id}")
    Single<Plan> getPlan(@Path("id") int planId);
}
