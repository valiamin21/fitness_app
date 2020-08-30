package ir.proglovving.fitapp.data_models;

import java.util.List;

public class DayDetailItem extends DayListItem{
    private List<ExerciseListItem> exerciseListItems;

    public List<ExerciseListItem> getExerciseListItems(){
        return exerciseListItems;
    }

    public void setExerciseListItems(List<ExerciseDetailItem> exerciseListItems){
        this.exerciseListItems.clear();
        this.exerciseListItems.addAll(exerciseListItems);
    }
}
