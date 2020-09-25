package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

public class Exercise {

    private int id;
    private String title;
    private String gif;
    private String description;
    private String walkthrough;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWalkthrough() {
        return walkthrough;
    }

    public void setWalkthrough(String walkthrough) {
        this.walkthrough = walkthrough;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getInvolvedMuscles() {
        return involvedMuscles;
    }

    public void setInvolvedMuscles(String involvedMuscles) {
        this.involvedMuscles = involvedMuscles;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }
}
