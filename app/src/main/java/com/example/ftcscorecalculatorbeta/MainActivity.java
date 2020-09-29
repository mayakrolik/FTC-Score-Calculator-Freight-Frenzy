package com.example.ftcscorecalculatorbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.transition.AutoTransition; //
import android.transition.TransitionManager; //
import android.widget.TableLayout; //
import androidx.cardview.widget.CardView; //

public class MainActivity extends AppCompatActivity {

    private TextView objResult;
    private TextView objAutresult;
    private TextView objTeleopresult;
    private TextView objEndresult;
    private EditText objTopGoals;
    private EditText objMiddleGoals;
    private EditText objLowGoals;
    private EditText objPowerShotsScored;
    private Switch objSwitchStoppedOnLine;
    private Switch objSwitchWobbleGoalDeposited;
    private EditText objTopGoalsTellyOp;
    private EditText objMiddleGoalsTellyOp;
    private EditText objLowGoalsTellyOp;
    private EditText objTopGoalsEndgame;
    private EditText objMiddleGoalsEndgame;
    private EditText objLowGoalsEndgame;
    private Switch objSwitchWobbleInDropZone;
    private Switch objSwitchStartLinePark;
    private ImageButton objAutonomousExpandButton;
    private TableLayout objAutonomousTable;
    private CardView objAutonomousCard;
    private ImageButton objTeleopsExpandButton;
    private TableLayout objTeleopsTable;
    private CardView objTeleopsCard;
    private ImageButton objEndExpandButton;
    private TableLayout objEndTable;
    private CardView objEndCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objResult = this.findViewById(R.id.textView2);
        objAutresult = this.findViewById(R.id.text_autonomous_score);
        objTeleopresult = this.findViewById(R.id.text_teleops_score);
        objEndresult = this.findViewById(R.id.text_endgame_score);

        TextWatcher objTW = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateTotals();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        };
        // region initialize event handlers
        objTopGoals = this.findViewById(R.id.editTextNumberTopGoals);
        objTopGoals.addTextChangedListener(objTW);

        objMiddleGoals = this.findViewById(R.id.editTextNumberMiddleGoals);
        objMiddleGoals.addTextChangedListener(objTW);

        objLowGoals = this.findViewById(R.id.editTextNumberLowGoals);
        objLowGoals.addTextChangedListener(objTW);

        objPowerShotsScored = this.findViewById(R.id.editTextNumberPowerShot);
        objPowerShotsScored.addTextChangedListener(objTW);

        objTopGoalsTellyOp = this.findViewById(R.id.editTextNumberTopGoalsTellyOp);
        objTopGoalsTellyOp.addTextChangedListener(objTW);

        objMiddleGoalsTellyOp = this.findViewById(R.id.editTextNumberMiddleGoalsTellyOp);
        objMiddleGoalsTellyOp.addTextChangedListener(objTW);

        objLowGoalsTellyOp = this.findViewById(R.id.editTextNumberLowGoalsTellyOp);
        objLowGoalsTellyOp.addTextChangedListener(objTW);

        objTopGoalsEndgame = this.findViewById(R.id.editTextNumberTopGoalsEnd);
        objTopGoalsEndgame.addTextChangedListener(objTW);

        objMiddleGoalsEndgame = this.findViewById(R.id.editTextNumberMiddleGoalsEnd);
        objMiddleGoalsEndgame.addTextChangedListener(objTW);

        objLowGoalsEndgame = this.findViewById(R.id.editTextNumberLowGoalsEnd);
        objLowGoalsEndgame.addTextChangedListener(objTW);

        objSwitchStoppedOnLine = this.findViewById(R.id.switchStoppedOnLine);
        objSwitchStoppedOnLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objSwitchWobbleGoalDeposited = this.findViewById(R.id.switchWobbleGoalDeposited);
        objSwitchWobbleGoalDeposited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objSwitchWobbleInDropZone = this.findViewById(R.id.switchWobbleInDropZone);
        objSwitchWobbleInDropZone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objSwitchStartLinePark = this.findViewById(R.id.switchStartLinePark);
        objSwitchStartLinePark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objAutonomousExpandButton = this.findViewById(R.id.autonomous_expand_card);

        objAutonomousTable = this.findViewById(R.id.autonomous_table);

        objAutonomousCard = this.findViewById(R.id.autonomous_cardview);

        objTeleopsExpandButton = this.findViewById(R.id.teleops_expand_card);

        objTeleopsTable = this.findViewById(R.id.teleops_table);

        objTeleopsCard = this.findViewById(R.id.CardViewteleops);

        objEndExpandButton = this.findViewById(R.id.endgame_expand_card);

        objEndTable = this.findViewById(R.id.endgame_table);

        objEndCard = this.findViewById(R.id.CardViewend);

        objAutonomousExpandButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (objAutonomousTable.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(objAutonomousCard,
                            new AutoTransition());
                    objAutonomousTable.setVisibility(View.GONE);
                    objAutonomousExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(objAutonomousCard,
                            new AutoTransition());
                    objAutonomousTable.setVisibility(View.VISIBLE);
                    objAutonomousExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        objTeleopsExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (objTeleopsTable.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(objTeleopsCard,
                            new AutoTransition());
                    objTeleopsTable.setVisibility(View.GONE);
                    objTeleopsExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(objTeleopsCard,
                            new AutoTransition());
                    objTeleopsTable.setVisibility(View.VISIBLE);
                    objTeleopsExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        objEndExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (objEndTable.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(objEndCard,
                            new AutoTransition());
                    objEndTable.setVisibility(View.GONE);
                    objEndExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(objEndCard,
                            new AutoTransition());
                    objEndTable.setVisibility(View.VISIBLE);
                    objEndExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
        // // // endregion initialize event handlers
    }


    private void updateTotals() {
        int intAutonomous = 0;
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objTopGoals.getText().toString()) * 12);
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objMiddleGoals.getText().toString()) * 6);
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objLowGoals.getText().toString()) * 3);
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objPowerShotsScored.getText().toString()) * 15);
        if (objSwitchStoppedOnLine.isChecked()) {
            intAutonomous = intAutonomous + 5;
        }

        if (objSwitchWobbleGoalDeposited.isChecked()) {
            intAutonomous = intAutonomous + 15;
        }

        int intTeleop = 0;
        intTeleop = intTeleop + (Integer.parseInt("0" + objTopGoalsTellyOp.getText().toString()) * 6);
        intTeleop = intTeleop + (Integer.parseInt("0" + objMiddleGoalsTellyOp.getText().toString()) * 4);
        intTeleop = intTeleop + (Integer.parseInt("0" + objLowGoalsTellyOp.getText().toString()) * 2);

        int intEnd = 0;
        intEnd = intEnd + (Integer.parseInt("0" + objTopGoalsEndgame.getText().toString()) * 6);
        intEnd = intEnd + (Integer.parseInt("0" + objMiddleGoalsEndgame.getText().toString()) * 4);
        intEnd = intEnd + (Integer.parseInt("0" + objLowGoalsEndgame.getText().toString()) * 2);
        if (objSwitchWobbleInDropZone.isChecked()) {
            intEnd = intEnd + 20;
        }

        if (objSwitchStartLinePark.isChecked()) {
            intEnd = intEnd + 5;
        }

        int intPoints = 0;

        intPoints = intAutonomous + intEnd + intTeleop;

        objResult.setText(Integer.toString(intPoints));
        objAutresult.setText(Integer.toString(intAutonomous));
        objTeleopresult.setText(Integer.toString(intTeleop));
        objEndresult.setText(Integer.toString(intEnd));

    }

}