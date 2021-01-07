package ir.proglovving.fitapp.viesws.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.proglovving.fitapp.Pagination;
import ir.proglovving.fitapp.R;
import ir.proglovving.fitapp.adapters.ExerciseItemsRecyclerAdapter;
import ir.proglovving.fitapp.api.ApiService;
import ir.proglovving.fitapp.api.RetrofitClient;
import ir.proglovving.fitapp.data_models.DayExercisesRequest;
import ir.proglovving.fitapp.data_models.Exercise;
import ir.proglovving.fitapp.viesws.activities.DayActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseListFragment extends Fragment implements Pagination {

    private static final String ARG_DAY_ID = "day_id";
    private static final String ARG_TOOLBAR_TITLE = "toolbar_title";
    private static final String ARG_DAY_MUSCLES = "toolbar_title";

    private int dayId;
    private String dayTitle;
    private String dayMuscles;


    private Toolbar toolbar;
    private RecyclerView exercisesRecyclerView;
    private ProgressBar paginationProgressBar;
    private Button startButton, resumeButton;


    ExerciseItemsRecyclerAdapter.ExerciseSelectionListener exerciseSelectionListener;
    OnExerciseStartListener onExerciseStartListener;

    private ExerciseItemsRecyclerAdapter exerciseItemsRecyclerAdapter;
    private ApiService apiService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String next = "initial";

    public ExerciseListFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(dayTitle);

        exercisesRecyclerView = view.findViewById(R.id.exercise_items_list_recyclerView);
        paginationProgressBar = view.findViewById(R.id.progressBar_pagination);

        startButton = view.findViewById(R.id.btn_start);
        resumeButton = view.findViewById(R.id.btn_resume);
    }

    public static ExerciseListFragment newInstance(int dayId, String toolbarTitle, String dayMuscles) {
        ExerciseListFragment fragment = new ExerciseListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_ID, dayId);
        args.putString(ARG_TOOLBAR_TITLE, toolbarTitle);
        args.putString(ARG_DAY_MUSCLES, dayMuscles);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayId = getArguments().getInt(ARG_DAY_ID);
            dayTitle = getArguments().getString(ARG_TOOLBAR_TITLE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            exerciseSelectionListener = (ExerciseItemsRecyclerAdapter.ExerciseSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExerciseSelectionListener");
        }

        try {
            onExerciseStartListener = (OnExerciseStartListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnExerciseStartListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        initViews(view);

        startButton.setOnClickListener(v -> onExerciseStartListener.onExerciseStart());
        resumeButton.setOnClickListener(v -> onExerciseStartListener.onExerciseResume());

        exerciseItemsRecyclerAdapter = new ExerciseItemsRecyclerAdapter(getContext(), this, DayActivity.exerciseList, exerciseSelectionListener);
        exercisesRecyclerView.setAdapter(exerciseItemsRecyclerAdapter);

        apiService = RetrofitClient.getApiService();
        onNextPage();

        return view;
    }

    @Override
    public void onNextPage() {
        if (next == null)
            return;

        paginationProgressBar.setVisibility(View.VISIBLE);
        Single<DayExercisesRequest> dayExercisesRequestSingleObservable;
        if (next.equals("initial")) {
            dayExercisesRequestSingleObservable = apiService.getDayExercises(dayId);
        } else {
            dayExercisesRequestSingleObservable = apiService.getDayExercises(next);
        }

        dayExercisesRequestSingleObservable
                .delay(1, TimeUnit.SECONDS) // TODO: 12/7/20 remove this line later
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DayExercisesRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(DayExercisesRequest dayExercisesRequest) {
                        paginationProgressBar.setVisibility(View.INVISIBLE);
                        next = dayExercisesRequest.getNextPage();

                        int insertStartPosition = DayActivity.exerciseList.size();
                        int insertItemCount = dayExercisesRequest.getExerciseList().size();
                        DayActivity.exerciseList.addAll(dayExercisesRequest.getExerciseList());
                        exerciseItemsRecyclerAdapter.notifyItemRangeInserted(insertStartPosition, insertItemCount);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "error in loading day exercises", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public interface OnExerciseStartListener {
        void onExerciseStart();

        void onExerciseResume();
    }
}