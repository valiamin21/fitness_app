package ir.proglovving.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.Utilities;
import ir.proglovving.fitapp.data_models.Day;
import ir.proglovving.fitapp.viesws.DayActivity;

public class DayItemsRecyclerAdapter extends RecyclerView.Adapter<DayItemsRecyclerAdapter.DayItemViewHolder> {

    private Context context;
    private List<Day> dayItemList = new ArrayList<>();
    private Pagination pagination;
    private int maxPaginationBound = 0;

    public DayItemsRecyclerAdapter(Context context, Pagination pagination) {
        this.context = context;
        this.pagination = pagination;
    }

    public void addItems(List<Day> dayItemList){
        this.dayItemList.addAll(dayItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayItemViewHolder holder, final int position) {
        holder.progressTextView.setText(Utilities.englishToPersianNumber(position * 2) + "%"); // todo put done exercises percent instead of position
        holder.titleTextView.setText(context.getString(R.string.day) + " " + Utilities.englishToPersianNumber(position + 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayActivity.start(context, dayItemList.get(position));
            }
        });

        if(position == dayItemList.size() - 1){
            if(maxPaginationBound < position){
                pagination.onNextPage();
            }else{
                maxPaginationBound = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dayItemList.size();
    }

    public class DayItemViewHolder extends RecyclerView.ViewHolder {
        private TextView progressTextView, titleTextView;

        public DayItemViewHolder(@NonNull View itemView) {
            super(itemView);
            progressTextView = itemView.findViewById(R.id.progress_tv);
            titleTextView = itemView.findViewById(R.id.title_tv);
        }
    }
}
