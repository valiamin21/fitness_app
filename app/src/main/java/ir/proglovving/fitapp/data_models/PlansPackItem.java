package ir.proglovving.fitapp.data_models;

import java.util.List;

public class PlansPackItem {
    private int id;
    private String title;
    private List<PlanListItem> planListItems;

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

    public List<PlanListItem> getPlanListItems() {
        return planListItems;
    }

    public void setPlanListItems(List<PlanListItem> planListItems) {
        this.planListItems = planListItems;
    }
}
