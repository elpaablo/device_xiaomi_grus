/*
* Copyright (C) 2018 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.lineageos.settings.display;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.utils.FileUtils;

public class HBMTileService extends TileService {

    private static final String HBM_ENABLE_KEY = "hbm_mode";
    private static final String HBM_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display-primary/hbm";

    private SharedPreferences.Editor mEditor;

    private void updateUI(boolean enabled) {
        final Tile tile = getQsTile();
        tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        boolean hbmStatus = "1".equals(FileUtils.readOneLine(HBM_NODE).trim());
        mEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        mEditor.putBoolean(HBM_ENABLE_KEY, hbmStatus);
        mEditor.commit();
        updateUI(hbmStatus);
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        boolean hbmStatus = "1".equals(FileUtils.readOneLine(HBM_NODE).trim());
        mEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        mEditor.putBoolean(HBM_ENABLE_KEY, !hbmStatus);
        mEditor.commit();
        FileUtils.writeLine(HBM_NODE, hbmStatus ? "0" : "1");
        updateUI(!hbmStatus);
    }
}
