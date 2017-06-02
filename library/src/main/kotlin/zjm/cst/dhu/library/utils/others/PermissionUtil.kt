package zjm.cst.dhu.library.utils.others

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

/**
 * Created by zjm on 2017/5/8.
 */
@TargetApi(23)
object PermissionUtil {

    var REQUEST_CODE = 1

    fun requestPermission(context: Activity, permissionList: List<String>) {
        var isGranted: Boolean = false
        for (i in permissionList.indices) {
            isGranted = ContextCompat.checkSelfPermission(context, permissionList[i]) !=
                    PackageManager.PERMISSION_GRANTED || isGranted
        }
        if (isGranted) {
            var isShow = false
            for (i in permissionList.indices) {
                isShow = ActivityCompat.shouldShowRequestPermissionRationale(context, permissionList[i])
                        || isShow
            }
            if (isShow) {
                AlertDialog.Builder(context)
                        .setMessage("Request permission")
                        .setPositiveButton("Yes") { _, _ ->
                            ActivityCompat.requestPermissions(context,
                                    permissionList.toTypedArray(), REQUEST_CODE)
                        }
                        .show()
            } else {
                ActivityCompat.requestPermissions(context, permissionList.toTypedArray(), REQUEST_CODE)
            }
        }
    }
}
