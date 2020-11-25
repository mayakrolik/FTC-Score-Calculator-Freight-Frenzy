package com.example.ftcscorecalculatorbeta.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ftcscorecalculatorbeta.R;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorViewModel;

public class TeamFragment extends Fragment {

    private TeamViewModel teamViewModel;

    final static String TAG = "TeamFragment";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_team, container, false);
        //doOnCreate(root);
        return root;
    }

}
