package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

public class DayItem {
    private int id;
    @SerializedName("name")
    private String title;
    private String muscle;

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

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }
}
