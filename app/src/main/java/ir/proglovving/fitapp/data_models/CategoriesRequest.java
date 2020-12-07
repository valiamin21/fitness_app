package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesRequest {
    @SerializedName("next")
    private String nextPage;

    @SerializedName("results")
    private List<CategoryItem> categoryItemList;

    public String getNextPage(){
        return nextPage;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }
}
