package ir.proglovving.fitapp.data_models;

import java.util.List;

public class Day extends DayItem {
    private List<ExerciseItem> exerciseItems;

    public List<ExerciseItem> getExerciseItems(){
        return exerciseItems;
    }

    public void setExerciseItems(List<Exercise> exerciseItems){
        this.exerciseItems.clear();
        this.exerciseItems.addAll(exerciseItems);
    }
}
