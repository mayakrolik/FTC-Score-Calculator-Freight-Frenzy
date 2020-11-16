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

import com.example.ftcscorecalculatorbeta.StringUtils;

public class Score {
    public int TeamNumber;
    public String TeamNickName;
    public String UserDisplayName;
    public String UserEmailAddress;
    public int AutScore;
    public int TelScore;
    public int EndScore;
    public int TotalScore;
    public int AutLowGoals;
    public int AutMiddleGoals;
    public boolean AutPowerShot1;
    public boolean AutPowerShot2;
    public boolean AutPowerShot3;
    public boolean AutStoppedOnLine;
    public int AutTopGoals;
    public boolean AutWobbleGoalDeposited;
    public int TelLowGoals;
    public int TelMiddleGoals;
    public int TelHighGoals;
    public int EndLowGoals;
    public int EndMiddleGoals;
    public int EndTopGoals;
    public boolean EndPowerShot1;
    public boolean EndPowerShot2;
    public boolean EndPowerShot3;
    public boolean EndStartLinePark;
    public int EndWobbleGoals;
    public boolean EndWobbleInDropZone;

/*
    City
    CountryCode
    CreatedTimestamp
    EndLowGoals
    EndMiddleGoals
    EndPowerShot1
    EndPowerShot2
    EndPowerShot3
    EndScore
    EndStartLinePark
    EndTopGoals
    EndWobbleGoals
    EndWobbleInDropZone
    PenaltyMajor
    PenaltyMinor
    PenaltyScore
    PostalCode
    SeasonYear
    StateProv
    TeamNickName
    TeamNumber
    TeamType
    TelLowGoals
    TelMiddleGoals
    TelScore
    TelTopGoals
    TotalScore
    UserUid
    UserDisplayName
    UserEmailAddress
*/

    public String getSafeUserDisplayInitials()
    {

        return UserDisplayName;
    }
    public String getSafeUserEmailAddress()
    {
        String astrix = "*";
        String safeemailstart = UserEmailAddress.substring(0,2);
        int indexofat = UserEmailAddress.indexOf('@');
        String safeemailend = UserEmailAddress.substring(indexofat-1);
        return (safeemailstart + StringUtils.repeat("*", indexofat-1) + safeemailend);

    }

    public Score(){

    }
}