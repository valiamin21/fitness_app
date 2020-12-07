package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("next")
    private String nextPage;

    @SerializedName("results")
    private List<PlanItem> planItemList;

    public String getNextPage(){
        return nextPage;
    }

    public List<PlanItem> getPlanItemList() {
        return planItemList;
    }
}
