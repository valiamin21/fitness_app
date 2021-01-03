package ir.proglovving.fitapp.viesws.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.proglovving.fitapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestDayFragment extends Fragment {

    public RestDayFragment() {
        // Required empty public constructor
    }

    public static RestDayFragment newInstance() {
        return new RestDayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_day, container, false);
        Button button = view.findViewById(R.id.rest_button);
        button.setOnClickListener(v -> getActivity().finish());
        return view;
    }
}