package com.kcal.settings.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    public static final String FILE_NAME_DEFAULT = "kcal_parts";

    public PrefManager(Context context, String fileName, int mode) {
        mPreferences = context.getSharedPreferences(fileName, mode);
        mEditor = mPreferences.edit();
    }

    public void setBoolean(String pref, Boolean val) {
        mEditor.putBoolean(pref, val);
        mEditor.commit();
    }

    public void setString(String pref, String val) {
        mEditor.putString(pref, val);
        mEditor.commit();
    }

    public void setInt(String pref, int val) {
        mEditor.putInt(pref, val);
        mEditor.commit();
    }

    public boolean getBoolean(String pref, boolean def) {
        return mPreferences.getBoolean(pref, def);
    }

    public void remove(String pref) {
        if (exists(pref)) {
            mEditor.remove(pref);
            mEditor.commit();
        }
    }

    public boolean exists(String pref) {
        return mPreferences.contains(pref);
    }

    public String getString(String pref, String def) {
            return mPreferences.getString(pref, def);
    }

    public int getInt(String pref, int def) {
        return mPreferences.getInt(pref, def);
    }
}
