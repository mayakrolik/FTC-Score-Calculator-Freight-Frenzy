package org.ftcteam14564.firstscorecalculator;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ftcteam14564.firstscorecalculator.data.model.States;
import org.ftcteam14564.firstscorecalculator.data.model.Team;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends DialogFragment {

    final static String TAG = "RegisterFragment";

    private int intSearchFailureCount = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        // Selection of the spinner
        Spinner spinner = (Spinner) root.findViewById(R.id.spinnerState);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, States.Names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        Button btnFindTeam = root.findViewById(R.id.findTeamButton);
        btnFindTeam.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText editTextTeamNumber = getView().findViewById(R.id.editTextTextTeamNumber);
                String strTeamNumber = editTextTeamNumber.getText().toString().trim();
                Log.d(TAG, "Team number entered = X" + strTeamNumber + "X");
                if (TextUtils.isEmpty(strTeamNumber))
                {
                    Toast.makeText(getContext(), "Enter a Team Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                CheckBox checkboxEULA = getView().findViewById(R.id.checkBoxEULA);
                if (!checkboxEULA.isChecked())
                {
                    Toast.makeText(getContext(), "You must agree to the EULA", Toast.LENGTH_SHORT).show();
                    return;
                }
                Spinner spinState = getView().findViewById(R.id.spinnerState);
                int intPosition = spinState.getSelectedItemPosition();
                String strStateAbbreviation = States.Abbreviations[intPosition];
                Log.d(TAG, "State abv = " + strStateAbbreviation);
                searchTeamByTeamNumberAndState(Integer.parseInt(strTeamNumber), strStateAbbreviation);
            }
        });
        // this.dismiss();
        return root;
    }

    private Team tenativeTeam = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void saveUserProfile()
    {
        MainActivity activity = (MainActivity)getActivity();

        Map<String, Object> user = new HashMap<>();
        user.put("UserUid", activity.currentUser.getUid());
        user.put("TeamNumber", tenativeTeam.TeamNumber);
        user.put("TeamNickName", tenativeTeam.NickName);
        user.put("LoginCount", 0);
        user.put("City", tenativeTeam.City);
        user.put("StateProv", tenativeTeam.StateProv);
        user.put("PostalCode", tenativeTeam.PostalCode);
        user.put("CountryCode", tenativeTeam.CountryCode);
        user.put("DisplayName", activity.currentUser.getDisplayName());
        user.put("EmailAddress", activity.currentUser.getEmail());
        user.put("ProgramCodeDisplay", tenativeTeam.ProgramCodeDisplay);
        user.put("ProgramName", tenativeTeam.ProgramName);

        // Add a new document with a generated ID
        db.collection("Users")
                .document(activity.currentUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Log.d(TAG, "Document saved.");
                        dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



    private void searchTeamByTeamNumberAndState(int intTeamNumber, String strStateAbbreviation)
    {
        db.collection("Teams")
                .whereEqualTo("TeamNumber", intTeamNumber)
                .whereEqualTo("StateProv", strStateAbbreviation)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                tenativeTeam = document.toObject(Team.class);
                                break;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        updateUI();
                    }
                });
    }

    private void updateUI()
    {
        if (tenativeTeam == null)
        {
            intSearchFailureCount++;

            Toast.makeText(getContext(), "Team Number / State search failed.\nTry again.", Toast.LENGTH_SHORT).show();

        } else {

            saveUserProfile();

        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        MainActivity activity = (MainActivity)getActivity();
        activity.updateUI();
    }
}