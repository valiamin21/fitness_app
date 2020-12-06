package ir.proglovving.fitapp.api;

import java.util.List;

import io.reactivex.Single;
import ir.proglovving.fitapp.data_models.CategoriesRequest;
import ir.proglovving.fitapp.data_models.Category;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.DayExercisesRequest;
import ir.proglovving.fitapp.data_models.FaqItemsRequest;
import ir.proglovving.fitapp.data_models.Plan;
import ir.proglovving.fitapp.data_models.PlanDaysRequest;
import ir.proglovving.fitapp.data_models.Tip;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {

    @GET("tips/")
    Single<List<Tip>> getTips();

    @GET("categories")
    Single<CategoriesRequest> getCategories();

    @GET
    Single<CategoriesRequest> getCategories(@Url String url);

    @GET("category/{id}")
    Single<Category> getCategory(@Path("id") int categoryId);

    @GET("plan/{id}")
    Single<Plan> getPlan(@Path("id") int planId);

    @GET("plan_days/{id}")
    Single<PlanDaysRequest> getPlanDays(@Path("id") int planId);

    @GET("day/{id}")
    Single<Day> getDay(@Path("id") int dayId);

    @GET("day_exercises/{id}")
    Single<DayExercisesRequest> getDayExercises(@Path("id") int dayId);

    @GET("faq")
    Single<FaqItemsRequest> getFaq();
}
