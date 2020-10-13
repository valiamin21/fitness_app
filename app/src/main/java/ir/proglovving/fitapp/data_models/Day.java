package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

public class Day {
    private int id;
    @SerializedName("name")
    private String title;
    private String muscle;
    @SerializedName("exercise_id")
    private int[] exercisesIds;

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

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setExercisesIds(int[] exercisesIds) {
        this.exercisesIds = exercisesIds;
    }

    public int[] getExercisesIds() {
        return exercisesIds;
    }
}
