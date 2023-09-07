/*
 * Copyright (C) 2020 The Xiaomi-SM6250 Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.kcal.settings;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.Runnable;
import java.util.List;

import com.kcal.settings.KCalUtils;
import com.kcal.settings.utils.FileUtils;
import com.kcal.settings.utils.PrefManager;

public class BootReceiver extends BroadcastReceiver implements KCalUtils {

    private static final String NIGHT_DISPLAY_ACTIVATED = "night_display_activated";
    private static final String TAG = "SettingsOnBoot";
    private boolean mSetupRunning = false;
    private Context mContext;

    public void onReceive(Context context, Intent intent) {

        mContext = context;
        PrefManager mPrefManager = new PrefManager(context.getApplicationContext(),
                PrefManager.FILE_NAME_DEFAULT, Context.MODE_PRIVATE);

        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos =
                activityManager.getRunningAppProcesses();
        for (int i = 0; i < procInfos.size(); i++) {
            if (procInfos.get(i).processName.equals("com.google.android.setupwizard")) {
                mSetupRunning = true;
            }
        }

        if (!mSetupRunning) {
            boolean kcalEnabled = mPrefManager.getBoolean(PREF_ENABLED, false);
            FileUtils.setValue(KCAL_ENABLE_PATH, kcalEnabled); //restore kcal status
            if(kcalEnabled) {
                // workaround for kcal, which is only working after triggering aosp color modes
                Settings.Secure.putInt(mContext.getContentResolver(), NIGHT_DISPLAY_ACTIVATED, 1);
                final Handler handlerNight = new Handler();
                handlerNight.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Settings.Secure.putInt(context.getContentResolver(), NIGHT_DISPLAY_ACTIVATED, 0);
                        Log.d("Handler", "Toggling night display mode...");
                    }
                }, 1000);
            }

            boolean setOnBoot =  mPrefManager.getBoolean(PREF_SETONBOOT, false);
            if (setOnBoot) {
                final Handler handlerKcal = new Handler();
                handlerKcal.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        applyKcalSavedSettings(mPrefManager, kcalEnabled);
                        Log.d("Handler", "Applying kcal settings...");
                    }
                }, 2000);
            }
        }
    }

    private void applyKcalSavedSettings(PrefManager mPrefManager, boolean kcalEnabled) {

        FileUtils.setValue(KCAL_ENABLE_PATH, kcalEnabled ? 1: 0);

        if(kcalEnabled) {
            int rValue = mPrefManager.getInt(PREF_RED, RED_DEFAULT);
            int gValue = mPrefManager.getInt(PREF_GREEN, GREEN_DEFAULT);
            int bValue = mPrefManager.getInt(PREF_BLUE, BLUE_DEFAULT);
            FileUtils.setValue(KCAL_RED_PATH, rValue);
            FileUtils.setValue(KCAL_GREEN_PATH, gValue);
            FileUtils.setValue(KCAL_BLUE_PATH, bValue);
            FileUtils.setValue(KCAL_SAT_PATH, mPrefManager.getBoolean(
                    PREF_GRAYSCALE, false) == true ? SATURATION_DEFAULT
                    : mPrefManager.getInt(PREF_SATURATION, SATURATION_DEFAULT)
                    + SATURATION_OFFSET);
            FileUtils.setValue(KCAL_VAL_PATH,
                    mPrefManager.getInt(PREF_VALUE, VALUE_DEFAULT) + VALUE_OFFSET);
            FileUtils.setValue(KCAL_CONT_PATH,mPrefManager.getInt(
                    PREF_CONTRAST, CONTRAST_DEFAULT) + CONTRAST_OFFSET);
            FileUtils.setValue(KCAL_HUE_PATH, mPrefManager.getInt(PREF_HUE, HUE_DEFAULT));
        }
    }

    private void showToast(String toastString, Context context) {
        Toast.makeText(context, toastString, Toast.LENGTH_SHORT)
                .show();
    }
}
