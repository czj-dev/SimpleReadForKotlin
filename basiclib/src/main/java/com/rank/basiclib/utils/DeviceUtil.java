package com.rank.basiclib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;


public class DeviceUtil {

    /**
     * 获取设备信息
     * 在6.0一下的机型可以通过Android_id→device_id→uuid来获取唯一标识
     * 在6.0以上为了避免权限问题则不通过device_id来确认信息。
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceInfo(Context context) {
        try {
            String device_id;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                if (TextUtils.isEmpty(device_id)) {
                    android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    device_id = tm.getDeviceId();
                }
            } else {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            if (TextUtils.isEmpty(device_id) || device_id.equals("9774d56d682e549c")) {
                device_id = Installation.id(context);
            }
            return device_id;
        } catch (Exception e) {
            return Installation.id(context);
        }
    }

    public static class Installation {
        private static String sID = null;
        private static final String INSTALLATION = "INSTALLATION";

        public synchronized static String id(Context context) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) writeInstallationFile(installation);
                    sID = readInstallationFile(installation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return sID;
        }

        private static String readInstallationFile(File installation) throws IOException {
            RandomAccessFile f = new RandomAccessFile(installation, "r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            f.close();
            return new String(bytes);
        }

        private static void writeInstallationFile(File installation) throws IOException {
            FileOutputStream out = new FileOutputStream(installation);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
            out.close();
        }
    }
}
