package ir.proglovving.fitapp.data_models;

import java.util.List;

public class Plan extends PlanItem {
    private List<DayItem> dayItems;

    public void setDayItems(List<DayItem> dayItems){
        this.dayItems.clear();
        this.dayItems.addAll(dayItems);
    }

    public List<DayItem> getDayItems(){
        return dayItems;
    }
}
