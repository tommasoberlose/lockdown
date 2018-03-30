package com.tommasoberlose.lockdown.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.view.LayoutInflater
import android.view.View
import com.tommasoberlose.lockdown.Constants.ACTION_ENTER_LOCKDOWN
import com.tommasoberlose.lockdown.R
import com.tommasoberlose.lockdown.receivers.DeviceAdminReceiver
import com.tommasoberlose.lockdown.utils.DeviceAdminUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (intent.action == ACTION_ENTER_LOCKDOWN) {
      DeviceAdminUtil.enterLockdown(this)
      finish()
    }

    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    updateUI()
  }

  fun updateUI() {
    val isPermissionGranted = DeviceAdminUtil.isAdminPermissionGranted(this)

    bottom_container_title.text = if (isPermissionGranted) getString(R.string.all_set_title) else getString(R.string.request_admin_permission_title)
    bottom_container_subtitle.text = if (isPermissionGranted) getString(R.string.all_set_msg) else getString(R.string.request_admin_permission_msg)
    bottom_container_action.text = if (isPermissionGranted) getString(R.string.action_enter_lockdown) else getString(R.string.action_grant_permission)

    if (isPermissionGranted) {
      bottom_container_action.setOnClickListener {
        DeviceAdminUtil.enterLockdown(this)
        finish()
      }
    } else {
      bottom_container_action.setOnClickListener {
        startActivityForResult(DeviceAdminUtil.getDeviceAdminPermissionIntent(this), DEVICE_ADMIN_PERMISSION_REQUEST_CODE)
      }
    }

//    action_more.setOnClickListener {
//      val bDialog = BottomSheetDialog(this)
//      val bView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_menu, null)
//
//      bView.findViewById(R.id.action_remove_p).setVisibility(if (Utils.checkAdmin(this)) View.VISIBLE else View.GONE)
//      bView.findViewById(R.id.action_remove_p).setOnClickListener(View.OnClickListener {
//        AlertDialog.Builder(this@Main)
//            .setTitle(resources.getString(R.string.text_attention))
//            .setMessage(resources.getString(R.string.ask_remove_privilege))
//            .setPositiveButton(android.R.string.yes) { _, _ ->
//              DeviceAdminUtil.removeAdminPremission(this)
//              updateUI()
//            }
//            .setNegativeButton(android.R.string.cancel, null).show()
//        bDialog.dismiss()
//      })
//
//      bView.findViewById(R.id.action_donation).setOnClickListener(View.OnClickListener {
//        startActivity(Intent(this, Donation::class.java))
//        bDialog.dismiss()
//      })
//
//      bDialog.setContentView(bView)
//      bDialog.show()
//    }
  }

  companion object {
    const val DEVICE_ADMIN_PERMISSION_REQUEST_CODE = 4
  }
}
