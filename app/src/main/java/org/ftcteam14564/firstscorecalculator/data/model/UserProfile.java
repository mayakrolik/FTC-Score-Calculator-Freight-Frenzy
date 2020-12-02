package org.ftcteam14564.firstscorecalculator.data.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class UserProfile {

    public UserProfile() {}

    public int TeamNumber;
    public String TeamNickName;
    public String UserUid;
    public int LoginCount;
    public Timestamp LastLogin;
    public String City;
    public String StateProv;
    public String PostalCode;
    public String CountryCode;
    public String DisplayName;
    public String EmailAddress;
    public String FirebaseMessagingToken;
    public String ProgramCodeDisplay;
    public String ProgramName;
    public String ProfilePhotoUrl;
    public List<FollowingTeam> FollowingTeams;

}
