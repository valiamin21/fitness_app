package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesRequest {
    private int count;
    @SerializedName("results")
    private List<CategoryItem> categoryItemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
}
