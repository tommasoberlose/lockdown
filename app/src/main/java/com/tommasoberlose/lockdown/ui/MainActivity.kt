package com.tommasoberlose.lockdown.ui

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.view.View
import com.tommasoberlose.lockdown.R
import com.tommasoberlose.lockdown.receivers.DeviceAdminReceiver
import com.tommasoberlose.lockdown.utils.DeviceAdminUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  val DEVICE_ADMIN_PERMISSION_REQUEST_CODE = 4
  val ACTION_ENTER_LOCKDOWN = "com.tommasoberlose.lockdown.action.ENTER_LOCKDOWN"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (intent.action == "android.intent.action.CREATE_SHORTCUT") {
      setResult(Activity.RESULT_OK, getAddIntent(this))
      finish()
    } else if (intent.action == ACTION_ENTER_LOCKDOWN) {
      DeviceAdminUtil.enterLockdown(this)
      finish()
    }

    setContentView(R.layout.activity_main)

    bottom_container_action.setOnClickListener {
        val mAdminName = ComponentName(this, DeviceAdminReceiver::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.text_request_dev))
        startActivityForResult(intent, DEVICE_ADMIN_PERMISSION_REQUEST_CODE)
    }

  }

  override fun onResume() {
    super.onResume()
    updateUI()
  }

  fun updateUI() {
    val isPermissionGranted = DeviceAdminUtil.isAdminPermissionGranted(this)

    bottom_container_title.text = if (isPermissionGranted) getString(R.string.all_set_title) else getString(R.string.request_admin_permission_title)
    bottom_container_subtitle.text = if (isPermissionGranted) getString(R.string.all_set_msg) else getString(R.string.request_admin_permission_msg)
    bottom_container_card.visibility = if (isPermissionGranted) View.GONE else View.VISIBLE
  }

  fun getAddIntent(context: Context): Intent {
    val shortcutIntent = Intent(context, MainActivity::class.java)
    shortcutIntent.action = ACTION_ENTER_LOCKDOWN
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
