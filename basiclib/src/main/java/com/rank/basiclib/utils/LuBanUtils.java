package com.rank.basiclib.utils;

import android.net.Uri;
import top.zibin.luban.Luban;

import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/19
 *     desc  :
 * </pre>
 */
public class LuBanUtils {


    private static final int IGNORE_SIZE = 200;


    public static File zip(File file) throws IOException {
        return Luban.with(Utils.getApp())
                .load(file)
                .ignoreBy(IGNORE_SIZE)
                .setFocusAlpha(true)
                .get().get(0);
    }

    public static File zip(Uri uri) throws IOException {
        return Luban.with(Utils.getApp())
                .load(uri)
                .ignoreBy(IGNORE_SIZE)
                .setFocusAlpha(true)
                .get().get(0);
    }

    public static File zip(String path) throws IOException {
        return Luban.with(Utils.getApp())
                .load(path)
                .ignoreBy(IGNORE_SIZE)
                .setFocusAlpha(true)
                .get().get(0);
    }
}
