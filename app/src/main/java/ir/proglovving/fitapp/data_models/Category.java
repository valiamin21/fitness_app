package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    private int count;
    @SerializedName("results")
    private List<PlanItem> planItemList;
}
