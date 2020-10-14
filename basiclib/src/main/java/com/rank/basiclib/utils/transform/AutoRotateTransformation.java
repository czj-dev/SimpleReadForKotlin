package com.rank.basiclib.utils.transform;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.rank.basiclib.ext.CommonExtKt;

import java.security.MessageDigest;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/12/14
 *     desc  :
 * </pre>
 */
public class AutoRotateTransformation extends BitmapTransformation {
    private static final String ID = "com.rank.basiclib.utils.transform.AutoRotateTransformation";
    private static final byte[] ID_BYTES = ID.getBytes();

    public AutoRotateTransformation() {
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = toTransform;
        // 如果图片的宽度大于屏幕的宽度则选择显示它
        if (outWidth > CommonExtKt.application().getResources().getDisplayMetrics().widthPixels) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90f);
            bitmap = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }
        return bitmap;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof AutoRotateTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}