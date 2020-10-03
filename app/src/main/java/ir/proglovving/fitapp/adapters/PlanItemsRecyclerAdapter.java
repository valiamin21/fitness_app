package ir.proglovving.fitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.PlanItem;
import ir.proglovving.fitapp.viesws.PlanItemsActivity;

public class PlanItemsRecyclerAdapter extends RecyclerView.Adapter<PlanItemsRecyclerAdapter.PlanItemViewHolder> {
    private Context context;
    private List<PlanItem> planItemList;

    public PlanItemsRecyclerAdapter(Context context, List<PlanItem> planItemList){
        this.context = context;
        this.planItemList = planItemList;
    }

    @NonNull
    @Override
    public PlanItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_plan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanItemViewHolder holder, final int position) {
        holder.planItemTextView.setText(planItemList.get(position).getTitle());
        Glide.with(context).load(planItemList.get(position).getImage()).into(holder.planItemImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 10/3/20
                Toast.makeText(context, "" + planItemList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return planItemList.size();
    }

    public class PlanItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView planItemImageView;
        private TextView planItemTextView;

        public PlanItemViewHolder(@NonNull View itemView) {
            super(itemView);
            planItemImageView = itemView.findViewById(R.id.plan_item_image);
            planItemTextView = itemView.findViewById(R.id.plan_item_title);
        }
    }
}
