package com.tommasoberlose.lockdown.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.tommasoberlose.lockdown.R
import com.tommasoberlose.lockdown.receivers.DeviceAdminReceiver
import com.tommasoberlose.lockdown.ui.activities.MainActivity

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

  fun getDeviceAdminPermissionIntent(context: Context): Intent {
    val mAdminName = ComponentName(context, DeviceAdminReceiver::class.java)
    val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName)
    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, context.getString(R.string.text_request_dev))
    return intent
  }

  fun removeAdminPremission(context: Context) {
    val devAdminReceiver = ComponentName(context, DeviceAdminReceiver::class.java)
    val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    dpm.removeActiveAdmin(devAdminReceiver)
  }
}