package ir.proglovving.fitapp.data_models;

public class DayListItem {
    private int id;
    private String title;
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
