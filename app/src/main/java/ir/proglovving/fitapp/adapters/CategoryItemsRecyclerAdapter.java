package ir.proglovving.fitapp.adapters;

import android.content.Context;
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
import ir.proglovving.fitapp.data_models.CategoryItem;

public class CategoryItemsRecyclerAdapter extends RecyclerView.Adapter<CategoryItemsRecyclerAdapter.CategoryItemViewHolder> {
    private Context context;
    private List<CategoryItem> categoryItemList;

    public CategoryItemsRecyclerAdapter(Context context, List<CategoryItem> categoryItemList){
        this.context = context;
        this.categoryItemList = categoryItemList;
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
                Toast.makeText(context, categoryItemList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
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
