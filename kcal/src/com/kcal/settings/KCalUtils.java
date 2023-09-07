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

public interface KCalUtils {
    String PREF_PRESETS = "kcal_presets";
    String PREF_PRESET_INDEX = "preset_index";
    String PREF_ENABLED = "kcal_enabled";
    String PREF_SETONBOOT = "set_on_boot";
    String PREF_RED = "color_red";
    String PREF_GREEN = "color_green";
    String PREF_BLUE = "color_blue";
    String PREF_SATURATION = "saturation";
    String PREF_VALUE = "value";
    String PREF_CONTRAST = "contrast";
    String PREF_HUE = "hue";
    String PREF_GRAYSCALE = "grayscale";

    boolean KCAL_ENABLED_DEFAULT = false;
    boolean SETONBOOT_DEFAULT = false;
    int RED_DEFAULT = 255;
    int GREEN_DEFAULT = 255;
    int BLUE_DEFAULT = 255;
    int SATURATION_DEFAULT = 50;
    int SATURATION_OFFSET = 220;
    int VALUE_DEFAULT = 127;
    int VALUE_OFFSET = 130;
    int CONTRAST_DEFAULT = 127;
    int CONTRAST_OFFSET = 132;
    int HUE_DEFAULT = 0;
    int GREYSCALE_SATURATION = 128;
    boolean GRAYSCALE_DEFAULT = false;

    String KCAL_ENABLE_PATH = "/sys/module/msm_drm/parameters/kcal_enable";
    String KCAL_RED_PATH =    "/sys/module/msm_drm/parameters/kcal_red";
    String KCAL_GREEN_PATH =  "/sys/module/msm_drm/parameters/kcal_green";
    String KCAL_BLUE_PATH =   "/sys/module/msm_drm/parameters/kcal_blue";
    String KCAL_SAT_PATH =    "/sys/module/msm_drm/parameters/kcal_sat";
    String KCAL_VAL_PATH =    "/sys/module/msm_drm/parameters/kcal_val";
    String KCAL_CONT_PATH =   "/sys/module/msm_drm/parameters/kcal_cont";
    String KCAL_HUE_PATH =    "/sys/module/msm_drm/parameters/kcal_hue";
}
