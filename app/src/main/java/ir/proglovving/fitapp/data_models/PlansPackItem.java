package ir.proglovving.fitapp.data_models;

import java.util.List;

public class PlansPackItem {
    private int id;
    private String title;
    private List<PlanItem> planItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PlanItem> getPlanItems() {
        return planItems;
    }

    public void setPlanItems(List<PlanItem> planItems) {
        this.planItems = planItems;
    }
}
