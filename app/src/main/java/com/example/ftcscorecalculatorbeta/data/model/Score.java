package com.example.ftcscorecalculatorbeta.data.model;


import com.example.ftcscorecalculatorbeta.StringUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.type.DateTime;

import java.util.List;

public class Score {
    @com.google.firebase.firestore.DocumentId
    public String DocumentId;
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
    public List<Kudo> Kudos;


    public String City;
    public String CountryCode;
    public Timestamp CreatedTimestamp;
    public int PenaltyMajor;
    public int PenaltyMinor;
    public int PenaltyScore;
    public String PostalCode;
    public int SeasonYear;
    public String StateProv;
    public String TeamType;
    public String UserUid;


    public String getSafeUserDisplayInitials()
    {
        String namefirstinitial = UserDisplayName.substring(0,1);
        int intexofspace = UserDisplayName.indexOf(' ');
        if (intexofspace < 0){
            return (namefirstinitial.toUpperCase() + ".");
        }
        String namelastinitial = UserDisplayName.substring(intexofspace+1, intexofspace+2);
        return (namefirstinitial.toUpperCase() + "." + namelastinitial.toUpperCase() + ".");

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