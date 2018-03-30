package com.tommasoberlose.lockdown.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import com.tommasoberlose.lockdown.Constants
import com.tommasoberlose.lockdown.R

class ShortcutsReceiver : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setResult(Activity.RESULT_OK, getAddIntent(this))
    finish()
  }

  fun getAddIntent(context: Context): Intent {
    val shortcutIntent = Intent(context, MainActivity::class.java)
    shortcutIntent.action = Constants.ACTION_ENTER_LOCKDOWN
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val shortcut = ShortcutInfoCompat.Builder(context, "action_enter_lockdown").setIntent(shortcutIntent)
        .setShortLabel(context.resources.getString(R.string.action_enter_lockdown))
        .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_shortcut))
        .setLongLabel(context.resources.getString(R.string.action_enter_lockdown))
        .build()

    return ShortcutManagerCompat.createShortcutResultIntent(context, shortcut)
  }
}
