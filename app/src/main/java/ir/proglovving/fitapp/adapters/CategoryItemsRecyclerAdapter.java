package ir.proglovving.fitapp.adapters;

import android.content.Context;
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

import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.CategoryItem;
import ir.proglovving.fitapp.viesws.activities.CategoryActivity;

public class CategoryItemsRecyclerAdapter extends RecyclerView.Adapter<CategoryItemsRecyclerAdapter.CategoryItemViewHolder> {
    private Context context;
    private Pagination pagination;
    private List<CategoryItem> categoryItemList = new ArrayList<>();
    private int maxPaginationBound = 0;

    public CategoryItemsRecyclerAdapter(Context context, Pagination pagination) {
        this.context = context;
        this.pagination = pagination;
    }

    public void addItems(List<CategoryItem> categoryItemList) {
        this.categoryItemList.addAll(categoryItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, final int position) {
        holder.categoryItemTextView.setText(categoryItemList.get(position).getName());
        Glide.with(context).load(categoryItemList.get(position).getImage()).into(holder.categoryItemImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryActivity.start(context, categoryItemList.get(position).getId(), categoryItemList.get(position).getName());
            }
        });

        if (position == categoryItemList.size() - 1) {
            if (maxPaginationBound < position) {
                pagination.onNextPage();
            } else {
                maxPaginationBound = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryItemImageView;
        private TextView categoryItemTextView;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemImageView = itemView.findViewById(R.id.category_item_image);
            categoryItemTextView = itemView.findViewById(R.id.category_item_title);
        }
    }
}
