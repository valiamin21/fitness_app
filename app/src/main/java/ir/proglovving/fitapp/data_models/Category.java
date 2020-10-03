package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    private int count;
    @SerializedName("results")
    private List<PlanItem> planItemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PlanItem> getPlanItemList() {
        return planItemList;
    }

    public void setPlanItemList(List<PlanItem> planItemList) {
        this.planItemList = planItemList;
    }
}
