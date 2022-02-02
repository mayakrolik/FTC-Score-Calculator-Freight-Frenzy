package org.ftcteam14564.firstscorecalculator.data.model;


import org.ftcteam14564.firstscorecalculator.StringUtils;
import com.google.firebase.Timestamp;

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
//    public int AutLowGoals;
//    public int AutMiddleGoals;
//    public boolean AutPowerShot1;
//    public boolean AutPowerShot2;
//    public boolean AutPowerShot3;
//    public boolean AutStoppedOnLine;
//    public int AutTopGoals;
//    public boolean AutWobbleGoalDeposited;
    public boolean AutDeliverDuck; // 10 points
    public boolean AutInStorageUnit; // 3 points
    public boolean AutCompletelyInStorageUnit; // 6
    public boolean AutInWarehouse; // 5
    public boolean AutCompletelyInWarehouse; // 10
    public boolean AutFreightStorageUnit; // 2
    public boolean AutFreightShippingHub; // 6
    public boolean AutPreLoadBox; // 10
    public boolean AutPreLoadUsingShippingEquipment; // 20
//    public int TelLowGoals;
//    public int TelMiddleGoals;
//    public int TelHighGoals;
    public int TelFreightStorageUnit; // 1
    public int TelLevel1; // 2
    public int TelLevel2; // 4
    public int TelLevel3; // 6
    public int TelFreightSharedShippingHub; // 4
//    public int EndLowGoals;
//    public int EndMiddleGoals;
//    public int EndTopGoals;
//    public boolean EndPowerShot1;
//    public boolean EndPowerShot2;
//    public boolean EndPowerShot3;
//    public boolean EndStartLinePark;
//    public int EndWobbleGoals;
//    public boolean EndWobbleInDropZone;
    public int EndFreightStorageUnit; // 1
    public int EndLevel1; // 2
    public int EndLevel2; // 4
    public int EndLevel3; // 6
    public int EndFreightSharedShippingHub; // 4
    public boolean EndElementDelivered; // 6
    public boolean EndAllianceShippingHubBalanced; // 10
    public boolean EndSharedShippingHubUnbalanced; // 20
    public boolean EndParkedInWarehouse; // 3
    public boolean EndParkedCompletelyInWarehouse; // 6
    public int EndNumberTeamShippingElements; // 15
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
    public String YouTubeVideoId;

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