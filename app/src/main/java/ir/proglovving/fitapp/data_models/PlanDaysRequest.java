package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanDaysRequest {
    @SerializedName("next")
    private String nextPage;

    @SerializedName("results")
    private List<Day> dayItemList;

    public String getNextPage(){
        return nextPage;
    }

    public List<Day> getDayItemList() {
        return dayItemList;
    }
}
