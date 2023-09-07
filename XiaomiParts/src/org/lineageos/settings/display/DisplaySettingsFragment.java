/*
 * Copyright (C) 2018 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use getContext() file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.display;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class DisplaySettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreference mDcDimmingPreference;
    private static final String DC_DIMMING_ENABLE_KEY = "dc_dimming_enable";
    private static final String DC_DIMMING_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display-primary/msm_fb_ea_enable";
    private SwitchPreference mHbmPreference;
    private static final String HBM_ENABLE_KEY = "hbm_mode";
    private static final String HBM_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display-primary/hbm";

    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.display_settings);
        mDcDimmingPreference = (SwitchPreference) findPreference(DC_DIMMING_ENABLE_KEY);
        if (FileUtils.fileExists(DC_DIMMING_NODE)) {
            mDcDimmingPreference.setEnabled(true);
            boolean dcStatus = "1".equals(FileUtils.readOneLine(DC_DIMMING_NODE).trim());
            mDcDimmingPreference.setChecked(dcStatus);
            mEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            mEditor.putBoolean(DC_DIMMING_ENABLE_KEY, dcStatus);
            mEditor.commit();
            mDcDimmingPreference.setOnPreferenceChangeListener(this);
        } else {
            mDcDimmingPreference.setSummary(R.string.dc_dimming_enable_summary_not_supported);
            mDcDimmingPreference.setEnabled(false);
        }
        mHbmPreference = (SwitchPreference) findPreference(HBM_ENABLE_KEY);
        if (FileUtils.fileExists(HBM_NODE)) {
            mHbmPreference.setEnabled(true);
            boolean hbmStatus = "1".equals(FileUtils.readOneLine(HBM_NODE).trim());
            mHbmPreference.setChecked(hbmStatus);
            mEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            mEditor.putBoolean(HBM_ENABLE_KEY, hbmStatus);
            mEditor.commit();
            mHbmPreference.setOnPreferenceChangeListener(this);
        } else {
            mHbmPreference.setSummary(R.string.hbm_enable_summary_not_supported);
            mHbmPreference.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (DC_DIMMING_ENABLE_KEY.equals(preference.getKey())) {
            boolean dcStatus = (Boolean) newValue;
            FileUtils.writeLine(DC_DIMMING_NODE, dcStatus ? "1" : "0");
            mEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            mEditor.putBoolean(DC_DIMMING_ENABLE_KEY, dcStatus);
            mEditor.commit();
        }
        if (HBM_ENABLE_KEY.equals(preference.getKey())) {
            boolean hbmStatus = (Boolean) newValue;
            FileUtils.writeLine(HBM_NODE, hbmStatus ? "1" : "0");
            mEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            mEditor.putBoolean(HBM_ENABLE_KEY, hbmStatus);
            mEditor.commit();
        }
        return true;
    }
}
