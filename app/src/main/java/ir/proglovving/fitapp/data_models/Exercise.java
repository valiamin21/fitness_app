package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Exercise implements Serializable {

    private int id;
    private String title;
    private String gif;
    private String description;
    private String walkthrough;
    @SerializedName("is_timed")
    private boolean isTimed;
    private int time;
    @SerializedName("muscles_involved")
    private String involvedMuscles;
    private float kcal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getGif() {
        return gif;
    }

    public String getDescription() {
        return description;
    }

    public String getWalkthrough() {
        return walkthrough;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public int getTime() {
        return time;
    }

    public String getInvolvedMuscles() {
        return involvedMuscles;
    }

    public float getKcal() {
        return kcal;
    }
}
