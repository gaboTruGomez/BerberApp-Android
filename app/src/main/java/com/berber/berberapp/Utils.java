package com.berber.berberapp;

/**
 * Created by gabotrugomez on 11/29/17.
 */

public class Utils {
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
