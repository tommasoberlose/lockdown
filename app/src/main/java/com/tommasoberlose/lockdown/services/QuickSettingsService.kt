package com.tommasoberlose.lockdown.services

import android.annotation.TargetApi
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.tommasoberlose.lockdown.Constants
import com.tommasoberlose.lockdown.R
import com.tommasoberlose.lockdown.ui.activities.MainActivity

@TargetApi(Build.VERSION_CODES.N)
class QuickSettingsService : TileService() {
  override fun onTileAdded() {
    super.onTileAdded()
    val tile = qsTile
    tile.icon = Icon.createWithResource(this, R.drawable.ic_stat_lock)
    tile.label = getString(R.string.action_enter_lockdown)
    tile.contentDescription = getString(R.string.app_name)
    tile.state = Tile.STATE_ACTIVE
    tile.updateTile()

    requestListeningState(this, ComponentName(this, QuickSettingsService::class.java))
  }

  override fun onClick() {
    val tile = qsTile
    val shortcutIntent = Intent(this, MainActivity::class.java)
    shortcutIntent.action = Constants.ACTION_ENTER_LOCKDOWN
    startActivityAndCollapse(shortcutIntent)
    tile.state = Tile.STATE_ACTIVE
    tile.updateTile()
  }

  override fun onStartListening() {
    super.onStartListening()
    val tile = qsTile
    tile.state = Tile.STATE_ACTIVE
    tile.updateTile()
  }
}
