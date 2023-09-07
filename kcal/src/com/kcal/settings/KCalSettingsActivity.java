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

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.kcal.settings.R;
import com.kcal.settings.utils.PrefManager;

public class KCalSettingsActivity extends Activity implements KCalUtils,
        AdapterView.OnItemSelectedListener {

    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Spinner spinnerPresets;

    private String[] mEntries;
    private CharSequence[] mEntryValues;
    private String mValue;

    private KCalSettingsFragment mKCalSettingsFragment;
    private PrefManager mPrefManager;

    private final int DEFAULT_PRESET_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcal);

        ActionBar ab = getActionBar();
        if (ab != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPrefManager = new PrefManager(getApplicationContext(),
                PrefManager.FILE_NAME_DEFAULT, Context.MODE_PRIVATE);

        setupImageSlider();

        mEntries = getResources().getStringArray(R.array.preset_entries);
        mEntryValues = getResources().getStringArray(R.array.preset_values);

        spinnerPresets = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(KCalSettingsActivity.this,
                android.R.layout.simple_spinner_item, mEntries);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresets.setAdapter(adapter);
        spinnerPresets.setSelection(mPrefManager.getInt(PREF_PRESET_INDEX, 0));
        spinnerPresets.setOnItemSelectedListener(this);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_kcal);
        if (fragment == null) {
            mKCalSettingsFragment = new KCalSettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_kcal, mKCalSettingsFragment)
                    .commit();
        } else {
            mKCalSettingsFragment = (KCalSettingsFragment) fragment;
        }
    }

    private void setupImageSlider() {
        viewPager = (ViewPager) findViewById(R.id.preview);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        com.kcal.settings.ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void resetCalibration(View v) {
        if (mKCalSettingsFragment != null) {
            mKCalSettingsFragment.applyValues(RED_DEFAULT + " " +
                    GREEN_DEFAULT + " " +
                    BLUE_DEFAULT + " " +
                    SATURATION_DEFAULT + " " +
                    VALUE_DEFAULT + " " +
                    CONTRAST_DEFAULT + " " +
                    HUE_DEFAULT);
            mKCalSettingsFragment.setmGrayscale(GRAYSCALE_DEFAULT);
            mKCalSettingsFragment.setmSetOnBoot(SETONBOOT_DEFAULT);
            mKCalSettingsFragment.setKcalEnabled(KCAL_ENABLED_DEFAULT);
            spinnerPresets.setSelection(DEFAULT_PRESET_INDEX);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == mPrefManager.getInt(PREF_PRESET_INDEX, 0))
            return;
        mKCalSettingsFragment.applyValues(mEntryValues[position].toString());
        mPrefManager.setInt(PREF_PRESET_INDEX, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
