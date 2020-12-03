package org.ftcteam14564.firstscorecalculator;

public class StringUtils{
    public static String repeat(String val, int count){
        StringBuilder buf = new StringBuilder(val.length() * count);
        while (count-- > 0) {
            buf.append(val);
        }
        return buf.toString();
    }

    public static String SafeUserDisplayInitials(String UserDisplayName)
    {
        String namefirstinitial = UserDisplayName.substring(0,1);
        int intexofspace = UserDisplayName.indexOf(' ');
        if (intexofspace < 0){
            return (namefirstinitial.toUpperCase() + ".");
        }
        String namelastinitial = UserDisplayName.substring(intexofspace+1, intexofspace+2);
        return (namefirstinitial.toUpperCase() + "." + namelastinitial.toUpperCase() + ".");

    }

    public static String SafeUserEmailAddress(String UserEmailAddress)
    {
        String astrix = "*";
        String safeemailstart = UserEmailAddress.substring(0,2);
        int indexofat = UserEmailAddress.indexOf('@');
        String safeemailend = UserEmailAddress.substring(indexofat-1);
        return (safeemailstart + StringUtils.repeat("*", indexofat-1) + safeemailend);
    }
}