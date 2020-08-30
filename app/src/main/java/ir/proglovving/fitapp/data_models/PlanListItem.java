package ir.proglovving.fitapp.data_models;

public class PlanListItem {
    private int id;
    private String title;
    private String image;
    private String level;
    private int daysCount;
    private int doneDaysCount;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public int getDoneDaysCount() {
        return doneDaysCount;
    }

    public void setDoneDaysCount(int doneDaysCount) {
        this.doneDaysCount = doneDaysCount;
    }

    public int getDonePercent(){
        return doneDaysCount * 100 / daysCount;
    }
}
