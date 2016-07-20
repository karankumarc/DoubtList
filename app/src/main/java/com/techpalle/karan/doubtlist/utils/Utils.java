package com.techpalle.karan.doubtlist.utils;

import java.text.CharacterIterator;
import java.text.SimpleDateFormat;

/**
 * Created by ADMIN on 7/5/2016.
 */
public class Utils {
    /**
     * Format the timestamp with SimpleDateFormat
     */
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Encode user email to use it as a Firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail", list and item "owner" value
     */
    public static String encodeEmail(String userEmail) {
        StringBuffer userEmailBuffer = new StringBuffer(userEmail);
        String username = removeAllDots(userEmailBuffer.substring(0,userEmailBuffer.indexOf("@")));
        String emailprovider = userEmailBuffer.substring(userEmailBuffer.indexOf("@")+1,userEmailBuffer.length());
        String finalEmail = username + "@" + emailprovider;
        return finalEmail.replace(".", ",");
    }

    private static String removeAllDots(String value){
        StringBuilder stringBuilder = new StringBuilder(value);
        for (int i = 0; i < stringBuilder.length(); i++) {
            if(stringBuilder.charAt(i)=='.'){
                stringBuilder.deleteCharAt(i);
                i--;
            }
        }
        return stringBuilder.toString();
    }
}
