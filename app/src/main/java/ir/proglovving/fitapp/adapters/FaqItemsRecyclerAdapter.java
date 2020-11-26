package ir.proglovving.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.FaqItem;

public class FaqItemsRecyclerAdapter extends RecyclerView.Adapter<FaqItemsRecyclerAdapter.FaqViewHolder> {

    private final Context context;
    private final List<FaqItem> faqItemList;

    public FaqItemsRecyclerAdapter(Context context, List<FaqItem> faqItemList) {
        this.context = context;
        this.faqItemList = faqItemList;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_faq_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        holder.setData(faqItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return faqItemList.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {

        private TextView questionTextView, answerTextView;
        private ImageButton arrowButton;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.faq_question);
            answerTextView = itemView.findViewById(R.id.faq_answer);
            arrowButton = itemView.findViewById(R.id.arrow_imageButton);
        }

        public void setData(FaqItem faqItem) {
            questionTextView.setText(faqItem.getQuestion());
            answerTextView.setText(faqItem.getAnswer());
        }
    }
}
