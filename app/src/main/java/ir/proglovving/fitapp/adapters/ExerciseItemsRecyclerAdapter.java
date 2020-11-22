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

import java.util.List;

import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.data_models.Exercise;

public class ExerciseItemsRecyclerAdapter extends RecyclerView.Adapter<ExerciseItemsRecyclerAdapter.ExerciseItemViewHolder> {

    private Context context;
    private List<Exercise> exerciseList;
    
    public ExerciseItemsRecyclerAdapter(Context context, List<Exercise> exerciseList){
        this.context = context;
        this.exerciseList = exerciseList;
    }
    
    @NonNull
    @Override
    public ExerciseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_exercise_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseItemViewHolder holder, int position) {
        holder.setData(exerciseList.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView exerciseImage, doneCheckImage;
        private TextView exerciseTitleTextView, exerciseTimeTextView;

        public ExerciseItemViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exercise_image);
            doneCheckImage = itemView.findViewById(R.id.image_doneCheck);
            exerciseTitleTextView = itemView.findViewById(R.id.tv_exerciseTitle);
            exerciseTimeTextView = itemView.findViewById(R.id.tv_exerciseTime);
        }
        
        public void setData(Exercise exercise){
            Glide.with(context).load(exercise.getGif()).into(exerciseImage);
            exerciseTitleTextView.setText(exercise.getTitle());
            exerciseTimeTextView.setText(exercise.getTime() / 60 + ":" + exercise.getTime() % 60);
        }
    }
}
