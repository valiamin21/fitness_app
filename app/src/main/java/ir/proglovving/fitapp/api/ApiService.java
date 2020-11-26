package ir.proglovving.fitapp.api;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ir.proglovving.fitapp.data_models.CategoriesPack;
import ir.proglovving.fitapp.data_models.Category;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.data_models.DayExercises;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.data_models.FaqItemsRequest;
import ir.proglovving.fitapp.data_models.Plan;
import ir.proglovving.fitapp.data_models.PlanDays;
import ir.proglovving.fitapp.data_models.Tip;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("tips/")
    Single<List<Tip>> getTips();

    @GET("categories")
    Single<CategoriesPack> getCategories();

    @GET("category/{id}")
    Single<Category> getCategory(@Path("id") int categoryId);

    @GET("plan/{id}")
    Single<Plan> getPlan(@Path("id") int planId);

    @GET("plan_days/{id}")
    Single<PlanDays> getPlanDays(@Path("id") int planId);

    @GET("day/{id}")
    Single<Day> getDay(@Path("id") int dayId);

    @GET("day_exercises/{id}")
    Single<DayExercises> getDayExercises(@Path("id") int dayId);

    @GET("faq")
    Single<FaqItemsRequest> getFaq();
}
