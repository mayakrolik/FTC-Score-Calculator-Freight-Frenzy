package com.example.ftcscorecalculatorbeta.ui.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.ftcscorecalculatorbeta.InputFilterMinMax;
import com.example.ftcscorecalculatorbeta.MainActivity;
import com.example.ftcscorecalculatorbeta.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel calculatorViewModel;

    final static String TAG = "CalculatorFragment";

    private TextView objResult;
    private TextView objAutresult;
    private TextView objTeleopresult;
    private TextView objEndresult;
    private TextView objPenaltyresult;
    private ImageButton objTopGoalsAdd;
    private ImageButton objTopGoalsSubtract;
    private EditText objTopGoals;
    private ImageButton objMiddleGoalsAdd;
    private ImageButton objMiddleGoalsSubtract;
    private EditText objMiddleGoals;
    private ImageButton objLowGoalsAdd;
    private ImageButton objLowGoalsSubtract;
    private EditText objLowGoals;
    private Switch objSwitchStoppedOnLine;
    private Switch objSwitchWobbleGoalDeposited;
    private ImageButton objTopGoalsTellyOpAdd;
    private ImageButton objTopGoalsTellyOpSubtract;
    private EditText objTopGoalsTellyOp;
    private ImageButton objMiddleGoalsTellyOpAdd;
    private ImageButton objMiddleGoalsTellyOpSubtract;
    private EditText objMiddleGoalsTellyOp;
    private ImageButton objLowGoalsTellyOpAdd;
    private ImageButton objLowGoalsTellyOpSubtract;
    private EditText objLowGoalsTellyOp;
    private ImageButton objTopGoalsEndgameAdd;
    private ImageButton objTopGoalsEndgameSubtract;
    private EditText objTopGoalsEndgame;
    private ImageButton objMiddleGoalsEndgameAdd;
    private ImageButton objMiddleGoalsEndgameSubtract;
    private EditText objMiddleGoalsEndgame;
    private ImageButton objLowGoalsEndgameAdd;
    private ImageButton objLowGoalsEndgameSubtract;
    private EditText objLowGoalsEndgame;
    private ImageButton objWobbleRingAdd;
    private ImageButton objWobbleRingSubtract;
    private EditText objWobbleRing;
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
    private CheckBox objPowerShot1;
    private CheckBox objPowerShot2;
    private CheckBox objPowerShot3;
    private CheckBox objPowerShot1End;
    private CheckBox objPowerShot2End;
    private CheckBox objPowerShot3End;
    private ImageButton objPenaltyExpandButton;
    private TableLayout objPenaltyTable;
    private CardView objPenaltyCard;
    private EditText objPenaltyMinor;
    private ImageButton objPenaltyMinorAdd;
    private ImageButton objPenaltyMinorSubtract;
    private EditText objPenaltyMajor;
    private ImageButton objPenaltyMajorAdd;
    private ImageButton objPenaltyMajorSubtract;
    private Button objSaveScores;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calculatorViewModel =
                ViewModelProviders.of(this).get(CalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        doOnCreate(root);
        return root;
    }

    private void doOnCreate(View view)
    {
        Button objLogout = view.findViewById(R.id.logoutbutton);

        objLogout.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        objSaveScores = view.findViewById(R.id.savescoresbutton);

        objSaveScores.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveScore();
            }
        });

        objResult = view.findViewById(R.id.textView2);
        objAutresult = view.findViewById(R.id.text_autonomous_score);
        objTeleopresult = view.findViewById(R.id.text_teleops_score);
        objEndresult = view.findViewById(R.id.text_endgame_score);
        objPenaltyresult = view.findViewById(R.id.text_penalty_score);

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

        objTopGoals = view.findViewById(R.id.text_top_goals);
        objTopGoals.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objTopGoals.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objTopGoalsAdd = view.findViewById(R.id.top_goals_add);
        objTopGoalsAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objTopGoals.getText().toString()) + 1;
                objTopGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objTopGoalsSubtract = view.findViewById(R.id.top_goals_subtract);
        objTopGoalsSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objTopGoals.getText().toString()) - 1, 0);
                objTopGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });


        objMiddleGoals = view.findViewById(R.id.text_middle_goals);
        objMiddleGoals.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objMiddleGoals.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objMiddleGoalsAdd = view.findViewById(R.id.middle_goals_add);
        objMiddleGoalsAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objMiddleGoals.getText().toString()) + 1;
                objMiddleGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objMiddleGoalsSubtract = view.findViewById(R.id.middle_goals_subtract);
        objMiddleGoalsSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objMiddleGoals.getText().toString()) - 1, 0);
                objMiddleGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objLowGoals = view.findViewById(R.id.text_low_goals);
        objLowGoals.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objLowGoals.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objLowGoalsAdd = view.findViewById(R.id.low_goals_add);
        objLowGoalsAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objLowGoals.getText().toString()) + 1;
                objLowGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objLowGoalsSubtract = view.findViewById(R.id.low_goals_subtract);
        objLowGoalsSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objLowGoals.getText().toString()) - 1, 0);
                objLowGoals.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objPowerShot1 = view.findViewById(R.id.CheckboxPowerShot1);
        objPowerShot1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objPowerShot2 = view.findViewById(R.id.CheckboxPowerShot2);
        objPowerShot2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objPowerShot3 = view.findViewById(R.id.CheckboxPowerShot3);
        objPowerShot3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objTopGoalsTellyOp = view.findViewById(R.id.text_top_goals_teleop);
        objTopGoalsTellyOp.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objTopGoalsTellyOp.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objTopGoalsTellyOpAdd = view.findViewById(R.id.top_goals_teleop_add);
        objTopGoalsTellyOpAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objTopGoalsTellyOp.getText().toString()) + 1;
                objTopGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objTopGoalsTellyOpSubtract = view.findViewById(R.id.top_goals_teleop_subtract);
        objTopGoalsTellyOpSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objTopGoalsTellyOp.getText().toString()) - 1, 0);
                objTopGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objMiddleGoalsTellyOp = view.findViewById(R.id.text_middle_goals_teleop);
        objMiddleGoalsTellyOp.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objMiddleGoalsTellyOp.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objMiddleGoalsTellyOpAdd = view.findViewById(R.id.middle_goals_teleop_add);
        objMiddleGoalsTellyOpAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objMiddleGoalsTellyOp.getText().toString()) + 1;
                objMiddleGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objMiddleGoalsTellyOpSubtract = view.findViewById(R.id.middle_goals_teleop_subtract);
        objMiddleGoalsTellyOpSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objMiddleGoalsTellyOp.getText().toString()) - 1, 0);
                objMiddleGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objLowGoalsTellyOp = view.findViewById(R.id.text_low_goals_teleop);
        objLowGoalsTellyOp.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objLowGoalsTellyOp.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objLowGoalsTellyOpAdd = view.findViewById(R.id.low_goals_teleop_add);
        objLowGoalsTellyOpAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objLowGoalsTellyOp.getText().toString()) + 1;
                objLowGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objLowGoalsTellyOpSubtract = view.findViewById(R.id.low_goals_teleop_subtract);
        objLowGoalsTellyOpSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objLowGoalsTellyOp.getText().toString()) - 1, 0);
                objLowGoalsTellyOp.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objTopGoalsEndgame = view.findViewById(R.id.text_top_goals_end);
        objTopGoalsEndgame.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objTopGoalsEndgame.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objTopGoalsEndgameAdd = view.findViewById(R.id.top_goals_end_add);
        objTopGoalsEndgameAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objTopGoalsEndgame.getText().toString()) + 1;
                objTopGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objTopGoalsEndgameSubtract = view.findViewById(R.id.top_goals_end_subtract);
        objTopGoalsEndgameSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objTopGoalsEndgame.getText().toString()) - 1, 0);
                objTopGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objMiddleGoalsEndgame = view.findViewById(R.id.text_middle_goals_end);
        objMiddleGoalsEndgame.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objMiddleGoalsEndgame.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objMiddleGoalsEndgameAdd = view.findViewById(R.id.middle_goals_end_add);
        objMiddleGoalsEndgameAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objMiddleGoalsEndgame.getText().toString()) + 1;
                objMiddleGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objMiddleGoalsEndgameSubtract = view.findViewById(R.id.middle_goals_end_subtract);
        objMiddleGoalsEndgameSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objMiddleGoalsEndgame.getText().toString()) - 1, 0);
                objMiddleGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objLowGoalsEndgame = view.findViewById(R.id.text_low_goals_end);
        objLowGoalsEndgame.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objLowGoalsEndgame.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objLowGoalsEndgameAdd = view.findViewById(R.id.low_goals_end_add);
        objLowGoalsEndgameAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objLowGoalsEndgame.getText().toString()) + 1;
                objLowGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objLowGoalsEndgameSubtract = view.findViewById(R.id.low_goals_end_subtract);
        objLowGoalsEndgameSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objLowGoalsEndgame.getText().toString()) - 1, 0);
                objLowGoalsEndgame.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objWobbleRing = view.findViewById(R.id.text_wobble_ring);
        objWobbleRing.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objWobbleRing.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "15")});

        objWobbleRingAdd = view.findViewById(R.id.wobble_ring_add);
        objWobbleRingAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objWobbleRing.getText().toString()) + 1;
                objWobbleRing.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objWobbleRingSubtract = view.findViewById(R.id.wobble_ring_subtract);
        objWobbleRingSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objWobbleRing.getText().toString()) - 1, 0);
                objWobbleRing.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objSwitchStoppedOnLine = view.findViewById(R.id.switchStoppedOnLine);
        objSwitchStoppedOnLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objSwitchWobbleGoalDeposited = view.findViewById(R.id.switchWobbleGoalDeposited);
        objSwitchWobbleGoalDeposited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objSwitchWobbleInDropZone = view.findViewById(R.id.switchWobbleInDropZone);
        objSwitchWobbleInDropZone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objSwitchStartLinePark = view.findViewById(R.id.switchStartLinePark);
        objSwitchStartLinePark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objPowerShot1End = view.findViewById(R.id.CheckboxPowerShot1End);
        objPowerShot1End.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objPowerShot2End = view.findViewById(R.id.CheckboxPowerShot2End);
        objPowerShot2End.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });

        objPowerShot3End = view.findViewById(R.id.CheckboxPowerShot3End);
        objPowerShot3End.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotals();
            }
        });
        objPenaltyMinor = view.findViewById(R.id.text_penalty_minor);
        objPenaltyMinor.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objPenaltyMinor.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});


        objPenaltyMinorAdd = view.findViewById(R.id.penalty_minor_add);
        objPenaltyMinorAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objPenaltyMinor.getText().toString()) + 1;
                objPenaltyMinor.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objPenaltyMinorSubtract = view.findViewById(R.id.penalty_minor_subtract);
        objPenaltyMinorSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objPenaltyMinor.getText().toString()) - 1, 0);
                objPenaltyMinor.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objPenaltyMajor = view.findViewById(R.id.text_penalty_major);
        objPenaltyMajor.addTextChangedListener(objTW);

        // filter any number that is negative or huge
        objPenaltyMajor.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        objPenaltyMajorAdd = view.findViewById(R.id.penalty_major_add);
        objPenaltyMajorAdd.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Integer.parseInt("0" + objPenaltyMajor.getText().toString()) + 1;
                objPenaltyMajor.setText(Integer.toString(intNewGoalPoints));
            }
        });
        objPenaltyMajorSubtract = view.findViewById(R.id.penalty_major_subtract);
        objPenaltyMajorSubtract.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intNewGoalPoints = Math.max(Integer.parseInt("0" + objPenaltyMajor.getText().toString()) - 1, 0);
                objPenaltyMajor.setText(Integer.toString(intNewGoalPoints));
            }
        });

        objAutonomousExpandButton = view.findViewById(R.id.autonomous_expand_card);
        objAutonomousTable = view.findViewById(R.id.autonomous_table);
        objAutonomousCard = view.findViewById(R.id.autonomous_cardview);
        objTeleopsExpandButton = view.findViewById(R.id.teleops_expand_card);
        objTeleopsTable = view.findViewById(R.id.teleops_table);
        objTeleopsCard = view.findViewById(R.id.CardViewteleops);
        objEndExpandButton = view.findViewById(R.id.endgame_expand_card);
        objEndTable = view.findViewById(R.id.endgame_table);
        objEndCard = view.findViewById(R.id.CardViewend);
        objPenaltyExpandButton = view.findViewById(R.id.penalty_expand_card);
        objPenaltyTable = view.findViewById(R.id.penalty_table);
        objPenaltyCard = view.findViewById(R.id.CardViewPenalty);
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
        objPenaltyExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (objPenaltyTable.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(objPenaltyCard,
                            new AutoTransition());
                    objPenaltyTable.setVisibility(View.GONE);
                    objPenaltyExpandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(objPenaltyCard,
                            new AutoTransition());
                    objPenaltyTable.setVisibility(View.VISIBLE);
                    objPenaltyExpandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
    }

    public void saveScore()
    {
        MainActivity activity = (MainActivity) getActivity();
        if (activity.currentUser == null) {
            activity.login();
            return;
        }

        int intTotalPoints = Integer.parseInt("0" + objResult.getText().toString());

        if (intTotalPoints <= 0)
        {
            // we don't save loosers ;)
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> score = new HashMap<>();
        score.put("UserUid", activity.currentUser.getUid());
        score.put("UserDisplayName", activity.userProfile.DisplayName);
        score.put("UserEmailAddress", activity.userProfile.EmailAddress);
        score.put("TeamNumber", activity.getMyTeam().TeamNumber);
        score.put("StateProv", activity.getMyTeam().StateProv);
        score.put("PostalCode", activity.getMyTeam().PostalCode);
        score.put("CountryCode", activity.getMyTeam().CountryCode);
        score.put("City", activity.getMyTeam().City);
        score.put("TeamNickName", activity.getMyTeam().NickName);
        score.put("TeamType", activity.getMyTeam().TeamType);
        score.put("SeasonYear", activity.seasonYear);



        score.put("TotalScore", intTotalPoints);
        score.put("CreatedTimestamp", FieldValue.serverTimestamp());


        score.put("AutTopGoals", (Integer.parseInt("0" + objTopGoals.getText().toString())));
        score.put("AutMiddleGoals", (Integer.parseInt("0" + objMiddleGoals.getText().toString())));
        score.put("AutLowGoals", (Integer.parseInt("0" + objLowGoals.getText().toString())));
        score.put("AutStoppedOnLine",  objSwitchStoppedOnLine.isChecked());
        score.put("AutWobbleGoalDeposited",  objSwitchWobbleGoalDeposited.isChecked());
        score.put("AutPowerShot1",  objPowerShot1.isChecked());
        score.put("AutPowerShot2",  objPowerShot2.isChecked());
        score.put("AutPowerShot3",  objPowerShot3.isChecked());
        score.put("AutScore", (Integer.parseInt("0" + objAutresult.getText().toString())));

        score.put("TelTopGoals", (Integer.parseInt("0" + objTopGoalsTellyOp.getText().toString())));
        score.put("TelMiddleGoals", (Integer.parseInt("0" + objMiddleGoalsTellyOp.getText().toString())));
        score.put("TelLowGoals", (Integer.parseInt("0" + objLowGoalsTellyOp.getText().toString())));
        score.put("TelScore", (Integer.parseInt("0" + objTeleopresult.getText().toString())));

        score.put("EndTopGoals", (Integer.parseInt("0" + objTopGoalsEndgame.getText().toString())));
        score.put("EndMiddleGoals", (Integer.parseInt("0" + objMiddleGoalsEndgame.getText().toString())));
        score.put("EndLowGoals", (Integer.parseInt("0" + objLowGoalsEndgame.getText().toString())));
        score.put("EndWobbleGoals", (Integer.parseInt("0" + objWobbleRing.getText().toString())));
        score.put("EndPowerShot1",  objPowerShot1End.isChecked());
        score.put("EndPowerShot2",  objPowerShot2End.isChecked());
        score.put("EndPowerShot3",  objPowerShot3End.isChecked());
        score.put("EndWobbleInDropZone",  objSwitchWobbleInDropZone.isChecked());
        score.put("EndStartLinePark",  objSwitchStartLinePark.isChecked());
        score.put("EndScore", (Integer.parseInt("0" + objEndresult.getText().toString())));

        score.put("PenaltyMajor", (Integer.parseInt("0" + objPenaltyMajor.getText().toString())));
        score.put("PenaltyMinor", (Integer.parseInt("0" + objPenaltyMinor.getText().toString())));
        score.put("PenaltyScore", (Integer.parseInt( objPenaltyresult.getText().toString())));


        // Add a new document with a generated ID
        db.collection("Scores")
                .add(score)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        // TODO :  reset everything to zero and show progress fragment
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void updateTotals() {
        int intAutonomous = 0;
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objTopGoals.getText().toString()) * 12);
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objMiddleGoals.getText().toString()) * 6);
        intAutonomous = intAutonomous + (Integer.parseInt("0" + objLowGoals.getText().toString()) * 3);
        int intTopGoals = 0;
        intAutonomous = intAutonomous + intTopGoals;

        if (objSwitchStoppedOnLine.isChecked()) {
            intAutonomous = intAutonomous + 5;
        }

        if (objSwitchWobbleGoalDeposited.isChecked()) {
            intAutonomous = intAutonomous + 15;
        }

        if (objPowerShot1.isChecked()) {
            intAutonomous = intAutonomous + 15;
        }

        if (objPowerShot2.isChecked()) {
            intAutonomous = intAutonomous + 15;
        }

        if (objPowerShot3.isChecked()) {
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
        intEnd = intEnd + (Integer.parseInt("0" + objWobbleRing.getText().toString()) * 5);
        if (objSwitchWobbleInDropZone.isChecked()) {
            intEnd = intEnd + 20;
        }

        if (objSwitchStartLinePark.isChecked()) {
            intEnd = intEnd + 5;
        }

        if (objPowerShot1End.isChecked()) {
            intEnd = intEnd + 15;
        }

        if (objPowerShot2End.isChecked()) {
            intEnd = intEnd + 15;
        }

        if (objPowerShot3End.isChecked()) {
            intEnd = intEnd + 15;
        }

        int intPenalty = 0;
        intPenalty = intPenalty + (Integer.parseInt("0" + objPenaltyMinor.getText().toString()) * -10);
        intPenalty = intPenalty + (Integer.parseInt("0" + objPenaltyMajor.getText().toString()) * -30);

        int intPoints = 0;

        intPoints = intAutonomous + intEnd + intTeleop + intPenalty;

        objResult.setText(Integer.toString(intPoints));
        objAutresult.setText(Integer.toString(intAutonomous));
        objTeleopresult.setText(Integer.toString(intTeleop));
        objEndresult.setText(Integer.toString(intEnd));
        objPenaltyresult.setText(Integer.toString(intPenalty));

    }
}