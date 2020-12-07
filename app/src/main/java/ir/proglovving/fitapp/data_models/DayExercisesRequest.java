package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayExercisesRequest {
    @SerializedName("next")
    private String nextPage;

    @SerializedName("results")
    private List<Exercise> exerciseList;

    public String getNextPage() {
        return nextPage;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
}
