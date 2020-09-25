package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

public class Day {
    private int id;
    private String title;
    private String muscle;
    @SerializedName("exercise_id")
    private int[] exercisesIds;
    private int exercisesCount;
    private int doneExercisesCount;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMuscle(String muscle){
        this.muscle = muscle;
    }

    public String getMuscle(){
        return muscle;
    }

    public void setExercisesIds(int[] exercisesIds){
        this.exercisesIds = exercisesIds;
    }

    public int[] getExercisesIds(){
        return exercisesIds;
    }

   public int getExercisesCount(){
        return exercisesCount;
   }

   public void setExercisesCount(int exercisesCount){
        this.exercisesCount = exercisesCount;
   }

   public int getDoneExercisesCount(){
        return doneExercisesCount;
   }

   public void setDoneExercisesCount(int doneExercisesCount){
        this.doneExercisesCount = doneExercisesCount;
   }

   public int getDonePercent(){
        return doneExercisesCount * 100 / exercisesCount;
   }
}
