package com.example.ftcscorecalculatorbeta.data.model;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ftcscorecalculatorbeta.R;
import com.example.ftcscorecalculatorbeta.MainActivity;
import com.example.ftcscorecalculatorbeta.ui.progress.ProgressViewModel;

public class Score {
    private ProgressViewModel progressViewModel;
    public String TeamId;
    public int AutScore;
    public int TelScore;
    public int EndScore;
    public int TotalScore;

    public Score(){

    }
}