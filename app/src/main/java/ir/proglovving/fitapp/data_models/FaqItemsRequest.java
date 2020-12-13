package ir.proglovving.fitapp.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqItemsRequest {

    @SerializedName("next")
    private String nextPage;

    @SerializedName("results")
    private List<FaqItem> faqItemList;

    public String getNextPage() {
        return nextPage;
    }

    public List<FaqItem> getFaqItemList() {
        return faqItemList;
    }
}
