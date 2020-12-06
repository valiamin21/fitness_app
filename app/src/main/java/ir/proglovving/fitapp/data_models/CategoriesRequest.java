package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesRequest {
    private String next;

    @SerializedName("results")
    private List<CategoryItem> categoryItemList;

    public String getNext(){
        return next;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }
}
