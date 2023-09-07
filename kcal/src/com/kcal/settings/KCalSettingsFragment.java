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

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.kcal.settings.utils.FileUtils;
import com.kcal.settings.preferences.CustomSeekBarPreference;
import com.kcal.settings.R;
import com.kcal.settings.utils.PrefManager;

public class KCalSettingsFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener, KCalUtils {

    private static final String TAG = "KCalSettingsFragment";
    private SwitchPreference mSetOnBoot;
    private SwitchPreference mEnabled;
    private CustomSeekBarPreference mRed;
    private CustomSeekBarPreference mGreen;
    private CustomSeekBarPreference mBlue;
    private CustomSeekBarPreference mSaturation;
    private CustomSeekBarPreference mValue;
    private CustomSeekBarPreference mContrast;
    private CustomSeekBarPreference mHue;
    private SwitchPreference mGrayscale;
    private int red, green, blue, sat, val, cont, hue;

    
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_kcal, rootKey);

        PrefManager mPrefManager = getDefaultSharedPreferencesManager();
        boolean enableGrayscale = mPrefManager.getBoolean(PREF_GRAYSCALE, false);
        boolean enableKcal = FileUtils.getAsInt(KCAL_ENABLE_PATH, 0) == 0
                ? false : true;
        mPrefManager.setBoolean(PREF_ENABLED, enableKcal); //sync with sysnode
        boolean setOnBoot = mPrefManager.getBoolean(PREF_SETONBOOT, SETONBOOT_DEFAULT);

        mSetOnBoot = (SwitchPreference) findPreference(PREF_SETONBOOT);
        mSetOnBoot.setChecked(setOnBoot);
        mSetOnBoot.setOnPreferenceChangeListener(this);

        mEnabled = (SwitchPreference) findPreference(PREF_ENABLED);
        mEnabled.setChecked(enableKcal);
        mEnabled.setOnPreferenceChangeListener(this);

        mRed = (CustomSeekBarPreference) findPreference(PREF_RED);
        mRed.setValue(FileUtils.getAsInt(KCAL_RED_PATH, RED_DEFAULT));
        mRed.setOnPreferenceChangeListener(this);

        mGreen = (CustomSeekBarPreference) findPreference(PREF_GREEN);
        mGreen.setValue(FileUtils.getAsInt(KCAL_GREEN_PATH, GREEN_DEFAULT));
        mGreen.setOnPreferenceChangeListener(this);

        mBlue = (CustomSeekBarPreference) findPreference(PREF_BLUE);
        mBlue.setValue(FileUtils.getAsInt(KCAL_BLUE_PATH, BLUE_DEFAULT));
        mBlue.setOnPreferenceChangeListener(this);

        mSaturation = (CustomSeekBarPreference) findPreference(PREF_SATURATION);
        mSaturation.setValue(FileUtils.getAsInt(KCAL_SAT_PATH, SATURATION_DEFAULT) - SATURATION_OFFSET);
        mSaturation.setEnabled(!enableGrayscale);
        mSaturation.setOnPreferenceChangeListener(this);

        mValue = (CustomSeekBarPreference) findPreference(PREF_VALUE);
        mValue.setValue(FileUtils.getAsInt(KCAL_VAL_PATH, VALUE_DEFAULT) - VALUE_OFFSET);
        mValue.setOnPreferenceChangeListener(this);

        mContrast = (CustomSeekBarPreference) findPreference(PREF_CONTRAST);
        mContrast.setValue(FileUtils.getAsInt(KCAL_CONT_PATH, CONTRAST_DEFAULT) - CONTRAST_OFFSET);
        mContrast.setOnPreferenceChangeListener(this);

        mHue = (CustomSeekBarPreference) findPreference(PREF_HUE);
        mHue.setValue(FileUtils.getAsInt(KCAL_HUE_PATH, HUE_DEFAULT));
        mHue.setOnPreferenceChangeListener(this);

        mGrayscale = (SwitchPreference) findPreference(PREF_GRAYSCALE);
        setmGrayscale(enableGrayscale);
        mGrayscale.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();

        PrefManager mPrefManager = getDefaultSharedPreferencesManager();
        boolean isGrayScale = false;

        switch (key) {
            case PREF_SETONBOOT:
                mPrefManager.setBoolean(PREF_SETONBOOT, (boolean) value);
                break;

            case PREF_ENABLED:
                boolean enabled = (boolean) value;
                FileUtils.setValue(KCAL_ENABLE_PATH, enabled ? 1 : 0);
                mPrefManager.setBoolean(PREF_ENABLED, enabled);
                if(enabled) updateSystem(mRed.getValue(), mGreen.getValue(),
                        mBlue.getValue(), mSaturation.getValue(), mValue.getValue(),
                        mContrast.getValue(), mHue.getValue(), mGrayscale.isChecked());
                break;

            case PREF_RED:
                red = (int) value;
                mPrefManager.setInt(PREF_RED, red);
                FileUtils.setValue(KCAL_RED_PATH, red);
                break;

            case PREF_GREEN:
                green = (int) value;
                mPrefManager.setInt(PREF_GREEN, green);
                FileUtils.setValue(KCAL_GREEN_PATH, green);
                break;

            case PREF_BLUE:
                blue = (int) value;
                mPrefManager.setInt(PREF_BLUE, blue);
                FileUtils.setValue(KCAL_BLUE_PATH, blue);
                break;

            case PREF_SATURATION:
                sat = (int) value;
                mPrefManager.setInt(PREF_SATURATION, sat);
                FileUtils.setValue(KCAL_SAT_PATH, sat + SATURATION_OFFSET);
                break;

            case PREF_VALUE:
                val = (int) value;
                mPrefManager.setInt(PREF_VALUE, val);
                FileUtils.setValue(KCAL_VAL_PATH, val + VALUE_OFFSET);
                break;

            case PREF_CONTRAST:
                cont = (int) value;
                mPrefManager.setInt(PREF_CONTRAST, cont);
                FileUtils.setValue(KCAL_CONT_PATH, cont + CONTRAST_OFFSET);
                break;

            case PREF_HUE:
                hue = (int) value;
                mPrefManager.setInt(PREF_HUE, hue);
                FileUtils.setValue(KCAL_HUE_PATH, hue);
                break;

            case PREF_GRAYSCALE:
                isGrayScale = (boolean) value;
                setmGrayscale(isGrayScale);
                break;

            default:
                break;
        }
        return true;
    }

    /*R, G, B, saturation, value, contrast, hue*/
    void applyValues(String preset) {
        String[] values = preset.split(" ");
        int red = Integer.parseInt(values[0]);
        int green = Integer.parseInt(values[1]);
        int blue = Integer.parseInt(values[2]);
        int sat = Integer.parseInt(values[3]);
        int value = Integer.parseInt(values[4]);
        int contrast = Integer.parseInt(values[5]);
        int hue = Integer.parseInt(values[6]);
        boolean isGrayscale = getDefaultSharedPreferencesManager()
                .getBoolean(PREF_GRAYSCALE, GRAYSCALE_DEFAULT);

        updatePreferences(red, green, blue, sat, value, contrast, hue);
        updateSystem(red, green, blue, sat, value, contrast, hue, isGrayscale);

        mRed.refresh(red);
        mGreen.refresh(green);
        mBlue.refresh(blue);
        mSaturation.refresh(sat);
        mValue.refresh(value);
        mContrast.refresh(contrast);
        mHue.refresh(hue);
    }

    void updateSystem(int red, int green, int blue, int sat, int val, int cont,
                      int hue, boolean isGreyscale) {

        final Handler handlerNight = new Handler(Looper.getMainLooper());
        handlerNight.postDelayed(new Runnable() {
            @Override
            public void run() {
                FileUtils.setValue(KCAL_RED_PATH, red);
                FileUtils.setValue(KCAL_GREEN_PATH, green);
                FileUtils.setValue(KCAL_BLUE_PATH, blue);
                FileUtils.setValue(KCAL_SAT_PATH, isGreyscale ? GREYSCALE_SATURATION
                        : sat + SATURATION_OFFSET);
                FileUtils.setValue(KCAL_VAL_PATH, val + VALUE_OFFSET);
                FileUtils.setValue(KCAL_CONT_PATH, cont + CONTRAST_OFFSET);
                FileUtils.setValue(KCAL_HUE_PATH, hue);
            }
        }, 500);
    }

    void updatePreferences(int red, int green, int blue, int sat, int val,
                           int cont, int hue) {

        PrefManager pm = getDefaultSharedPreferencesManager();
        pm.setInt(PREF_RED, red);
        pm.setInt(PREF_GREEN, green);
        pm.setInt(PREF_BLUE, blue);
        pm.setInt(PREF_SATURATION, sat);
        pm.setInt(PREF_VALUE, val);
        pm.setInt(PREF_CONTRAST, cont);
        pm.setInt(PREF_HUE, hue);
    }

    void setKcalEnabled(boolean enabled) {
        getDefaultSharedPreferencesManager().setBoolean(PREF_ENABLED, enabled);
        mEnabled.setChecked(enabled);
        FileUtils.setValue(KCAL_ENABLE_PATH, enabled);
    }

    void setmSetOnBoot(boolean checked) {
        mSetOnBoot.setChecked(checked);
        getDefaultSharedPreferencesManager().setBoolean(PREF_SETONBOOT, checked);
    }

    void setmGrayscale(boolean checked) {
        mGrayscale.setChecked(checked);
        mSaturation.setEnabled(!checked);
        PrefManager pm = getDefaultSharedPreferencesManager();
        pm.setBoolean(PREF_GRAYSCALE, checked);
        FileUtils.setValue(KCAL_SAT_PATH, checked ? GREYSCALE_SATURATION :
                pm.getInt(PREF_SATURATION, SATURATION_DEFAULT)
                + SATURATION_OFFSET);
    }

    PrefManager getDefaultSharedPreferencesManager() {
        return new PrefManager(getActivity().getApplicationContext(), PrefManager.FILE_NAME_DEFAULT,
                Context.MODE_PRIVATE);
    }
}
