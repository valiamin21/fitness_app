package ir.proglovving.fitapp.adapters;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.PlanItem;
import ir.proglovving.fitapp.viesws.PlanActivity;

public class PlanItemsRecyclerAdapter extends RecyclerView.Adapter<PlanItemsRecyclerAdapter.PlanItemViewHolder> {
    private Context context;
    private List<PlanItem> planItemList = new ArrayList<>();
    private Pagination pagination;
    private int maxPaginationBound = 0;

    public PlanItemsRecyclerAdapter(Context context, Pagination pagination) {
        this.context = context;
        this.pagination = pagination;
    }

    public void addItems(List<PlanItem> planItemList){
        this.planItemList.addAll(planItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_plan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanItemViewHolder holder, final int position) {
        holder.setData(planItemList.get(position));

        if(position == planItemList.size() - 1){
            if(maxPaginationBound < position){
                pagination.onNextPage();
            }else{
                maxPaginationBound = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return planItemList.size();
    }

    public class PlanItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView planItemImageView;
        private TextView planItemTextView, totalDaysTextView, levelTextView, percentTextView;

        public PlanItemViewHolder(@NonNull View itemView) {
            super(itemView);
            planItemImageView = itemView.findViewById(R.id.plan_item_image);
            planItemTextView = itemView.findViewById(R.id.plan_item_title);
            totalDaysTextView = itemView.findViewById(R.id.tv_totalDay);
            levelTextView = itemView.findViewById(R.id.tv_level);
            percentTextView = itemView.findViewById(R.id.tv_percent);
        }

        public void setData(final PlanItem planItem){
            planItemTextView.setText(planItem.getTitle());
            Glide.with(context).load(planItem.getImage()).into(planItemImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlanActivity.start(context, planItem.getId());
                }
            });
            totalDaysTextView.setText(planItem.getDaysCount() + " روزه");
            levelTextView.setText("سطح " + planItem.getLevel());
            // TODO: 11/11/20  set real percent to percentTextView
            percentTextView.setText(new Random(SystemClock.currentThreadTimeMillis()).nextInt(20) * 5 + "%");
        }
    }
}
