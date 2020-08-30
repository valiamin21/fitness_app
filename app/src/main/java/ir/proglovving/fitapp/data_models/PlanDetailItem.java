package ir.proglovving.fitapp.data_models;

import java.util.List;

public class PlanDetailItem extends PlanListItem{
    private List<DayListItem> dayListItems;

    public void setDayListItems(List<DayListItem> dayListItems){
        this.dayListItems.clear();
        this.dayListItems.addAll(dayListItems);
    }

    public List<DayListItem> getDayListItems(){
        return dayListItems;
    }
}
