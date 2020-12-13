package ir.proglovving.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.FaqItem;

public class FaqItemsRecyclerAdapter extends RecyclerView.Adapter<FaqItemsRecyclerAdapter.FaqViewHolder> {

    private final Context context;
    private List<FaqItem> faqItemList = new ArrayList<>();
    private Pagination pagination;
    private int maxPaginationBound = 0;

    public FaqItemsRecyclerAdapter(Context context, Pagination pagination) {
        this.context = context;
        this.pagination = pagination;
    }

    public void addItems(List<FaqItem> faqItemList) {
        int insertStartPosition = this.faqItemList.size();
        int itemsCount = faqItemList.size();
        this.faqItemList.addAll(faqItemList);
        notifyItemRangeInserted(insertStartPosition, itemsCount);
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_faq_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        holder.setData(faqItemList.get(position), position);
        if (position == getItemCount() - 1) {
            if (maxPaginationBound < position) {
                pagination.onNextPage();
            } else {
                maxPaginationBound = position;
            }
        }
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

        void expandToggle(FaqItem faqItem, int position) {
            faqItem.setExpanded(!faqItem.isExpanded());
            notifyItemChanged(position);
        }

        public void setData(final FaqItem faqItem, final int position) {
            questionTextView.setText(faqItem.getQuestion());
            answerTextView.setText(faqItem.getAnswer());
            if (faqItem.isExpanded()) {
                answerTextView.setVisibility(View.VISIBLE);
                arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            } else {
                answerTextView.setVisibility(View.GONE);
                arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }


            arrowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandToggle(faqItem, position);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandToggle(faqItem, position);
                }
            });
        }
    }
}
