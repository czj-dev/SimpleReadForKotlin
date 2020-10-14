package com.rank.basiclib.utils;

/**
 * <pre>
 *     author: Windows XP
 *     time  : 2019/4/10
 *     desc  :
 * </pre>
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 权限请求页适配，不同手机系统跳转到不同的权限请求页
 *
 * @author Donkor
 */

public class PermissionPageUtils {
    private final String TAG = "PermissionPageManager";
    //自己的项目包名
    private String packageName = "com.jiabeiplus.smart.refuel";
    private static PermissionPageUtils utils;

    public static PermissionPageUtils getInstance() {
        if (utils == null) {
            utils = new PermissionPageUtils();
        }
        return utils;
    }

    private PermissionPageUtils() {

    }

    public void jumpPermissionPage() {
        String name = Build.MANUFACTURER;
        switch (name) {
            case "HUAWEI":
                goHuaWeiManager();
                break;
            case "vivo":
                goVivoManager();
                break;
            case "OPPO":
                goOppoManager();
                break;
            case "Coolpad":
                goCoolpadManager();
                break;
            case "Meizu":
                goMeizuManager();
                break;
            case "Xiaomi":
                goXiaoMiManager();
                break;
            case "samsung":
                goSangXinManager();
                break;
            case "Sony":
                goSonyManager();
                break;
            case "LG":
                goLGManager();
                break;
            default:
                goIntentSetting();
                break;
        }
    }

    private void goLGManager() {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSonyManager() {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goHuaWeiManager() {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }


    private void goXiaoMiManager() {
        String rom = getMiuiVersion();
        Intent intent = new Intent();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
            Utils.getApp().startActivity(intent);
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
            Utils.getApp().startActivity(intent);
        } else {
            goIntentSetting();
        }
    }

    private void goMeizuManager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSangXinManager() {
        //三星4.3可以直接跳转
        goIntentSetting();
    }

    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", Utils.getApp().getPackageName(), null);
        intent.setData(uri);
        try {
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goOppoManager() {
        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private void goCoolpadManager() {
        doStartApplicationWithPackageName("com.yulong.android.security:remote");
      /*  Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private void goVivoManager() {
        doStartApplicationWithPackageName("com.bairenkeji.icaller");
     /*   Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
    }


    private void doStartApplicationWithPackageName(String packagename) {
        try {
            // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
            PackageInfo packageinfo = null;
            packageinfo = Utils.getApp().getPackageManager().getPackageInfo(packagename, 0);
            if (packageinfo == null) {
                return;
            }
            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);
            // 通过getPackageManager()的queryIntentActivities方法遍历
            List<ResolveInfo> resolveinfoList = Utils.getApp().getPackageManager()
                    .queryIntentActivities(resolveIntent, 0);
            Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
            for (int i = 0; i < resolveinfoList.size(); i++) {
                Log.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
            }
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                // packageName参数2 = 参数 packname
                String packageName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 设置ComponentName参数1:packageName参数2:MainActivity路径
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);

                Utils.getApp().startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();

        }
    }
}
