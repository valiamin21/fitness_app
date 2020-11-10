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

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.DayItem;

public class DayItemsRecyclerAdapter extends RecyclerView.Adapter<DayItemsRecyclerAdapter.DayItemViewHolder> {

    private Context context;
    private List<DayItem> dayItemList;

    public DayItemsRecyclerAdapter(Context context, List<DayItem> dayItemList){
        this.context = context;
        this.dayItemList = dayItemList;
    }

    @NonNull
    @Override
    public DayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_day_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayItemViewHolder holder, int position) {
        holder.progressTextView.setText(String.valueOf(dayItemList.get(position).getId())); // todo put done exercises percent instead of id
        holder.titleTextView.setText(dayItemList.get(position).getTitle());
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
