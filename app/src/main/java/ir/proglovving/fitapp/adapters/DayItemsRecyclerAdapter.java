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
import ir.proglovving.fitapp.data_models.Day;

public class DayItemsRecyclerAdapter extends RecyclerView.Adapter<DayItemsRecyclerAdapter.DayItemViewHolder> {

    private Context context;
    private List<Day> dayList = new ArrayList<>();

    public DayItemsRecyclerAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public DayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_day_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayItemViewHolder holder, int position) {
        holder.progressTextView.setText(String.valueOf(dayList.get(position).getId())); // todo put done exercises percent instead of id
        holder.titleTextView.setText(dayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public void addItem(Day day){
        dayList.add(day);
        notifyItemInserted(dayList.size());
//        notifyDataSetChanged();
    }

    public void clearItems(){
        dayList.clear();
        notifyDataSetChanged();
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
