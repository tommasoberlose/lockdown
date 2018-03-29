package com.tommasoberlose.lockdown.receivers

import android.content.Context
import android.content.Intent
import com.tommasoberlose.lockdown.R


class DeviceAdminReceiver : android.app.admin.DeviceAdminReceiver() {
  override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
    return context.getString(R.string.remove_admin_permission_warning)
  }
}
