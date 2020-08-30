package ir.proglovving.fitapp.data_models;

public class Exercise extends ExerciseItem {
    private String description;
    private String walkthrough;
    private String involvedMuscles;
    private int KCalories;
    private int level;

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

    public String getInvolvedMuscles() {
        return involvedMuscles;
    }

    public void setInvolvedMuscles(String involvedMuscles) {
        this.involvedMuscles = involvedMuscles;
    }

    public int getKCalories() {
        return KCalories;
    }

    public void setKCalories(int KCalories) {
        this.KCalories = KCalories;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
