package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanDays {
    private int count;
    private int next;
    private int previous;
    @SerializedName("results")
    private List<DayItem> dayItemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public List<DayItem> getDayItemList() {
        return dayItemList;
    }

    public void setDayItemList(List<DayItem> dayItemList) {
        this.dayItemList = dayItemList;
    }
}
