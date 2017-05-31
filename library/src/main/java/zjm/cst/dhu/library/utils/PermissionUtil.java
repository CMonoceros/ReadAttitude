package zjm.cst.dhu.library.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * Created by zjm on 2017/5/8.
 */
@TargetApi(23)
public class PermissionUtil {

    public static int REQUEST_CODE = 1;

    public static void requestPermission(final Activity context, final List<String> permissionList) {
        Boolean isGranted = false;
        for (int i = 0; i < permissionList.size(); i++) {
            isGranted = ContextCompat.checkSelfPermission(context, permissionList.get(i)) != PackageManager.PERMISSION_GRANTED || isGranted;
        }
        if (isGranted) {
            boolean isShow = false;
            for (int i = 0; i < permissionList.size(); i++) {
                isShow = ActivityCompat.shouldShowRequestPermissionRationale(context, permissionList.get(i)) || isShow;
            }
            if (isShow) {
                new AlertDialog.Builder(context)
                        .setMessage("Request permission")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(context, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(context, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);
            }
        }
    }
}
