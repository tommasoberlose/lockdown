package com.tommasoberlose.lockdown.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.tommasoberlose.lockdown.receivers.DeviceAdminReceiver
import com.tommasoberlose.lockdown.ui.MainActivity

object DeviceAdminUtil {
  fun isAdminPermissionGranted(context: Context): Boolean {
    val mDPM = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val mAdminName = ComponentName(context, DeviceAdminReceiver::class.java)
    return mDPM.isAdminActive(mAdminName)
  }

  fun enterLockdown(context: Context) {
    val policyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val adminReceiver = ComponentName(context, DeviceAdminReceiver::class.java)
    val admin = policyManager.isAdminActive(adminReceiver)
    if (admin) {
      policyManager.lockNow()
    } else {
      context.startActivity(Intent(context, MainActivity::class.java))
    }
  }
}